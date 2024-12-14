package top

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

class add1 extends Module {
  val io = IO(new Bundle {
    val a    = Input(Bool())
    val b    = Input(Bool())
    val cin  = Input(Bool())
    val sum  = Output(Bool())
    val cout = Output(Bool())
  })

  io.sum  := io.a ^ io.b ^ io.cin                              // 和
  io.cout := (io.a & io.b) | (io.a & io.cin) | (io.b & io.cin) // 进位
}

class add16 extends Module {
  val io = IO(new Bundle {
    val a    = Input(UInt(16.W))
    val b    = Input(UInt(16.W))
    val cin  = Input(Bool())
    val sum  = Output(UInt(16.W))
    val cout = Output(Bool())
  })

  val carry = Wire(Vec(16, Bool()))
  val sum   = Wire(Vec(16, Bool()))

  val add1_0 = Module(new add1)
  add1_0.io.a   := io.a(0)
  add1_0.io.b   := io.b(0)
  add1_0.io.cin := io.cin
  sum(0)        := add1_0.io.sum
  carry(0)      := add1_0.io.cout

  for (i <- 1 until 16) {
    val add1 = Module(new add1)
    add1.io.a   := io.a(i)
    add1.io.b   := io.b(i)
    add1.io.cin := carry(i - 1)
    sum(i)      := add1.io.sum
    carry(i)    := add1.io.cout
  }

  io.cout := carry(15)
  io.sum  := sum.asUInt
}

/** Top module
  */
class Top extends Module {
  val io = IO(new Bundle {
// start
    val a   = Input(UInt(32.W))
    val b   = Input(UInt(32.W))
    val sum = Output(UInt(32.W))
  })

  val loCout = Wire(Bool())
  val loSum  = Wire(UInt(16.W))
  val hiCout = Wire(Bool())
  val hiSum  = Wire(UInt(16.W))

  val add16_0 = Module(new add16)
  add16_0.io.a   := io.a(15, 0)
  add16_0.io.b   := io.b(15, 0)
  add16_0.io.cin := false.B
  loSum          := add16_0.io.sum
  loCout         := add16_0.io.cout

  val add16_1 = Module(new add16)
  add16_1.io.a   := io.a(31, 16)
  add16_1.io.b   := io.b(31, 16)
  add16_1.io.cin := loCout
  hiSum          := add16_1.io.sum
  hiCout         := add16_1.io.cout

  io.sum := Cat(hiSum, loSum)
}
