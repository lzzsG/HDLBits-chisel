package top

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

class Mux2to1(val width: Int) extends Module {
  val io = IO(new Bundle {
    val in0 = Input(UInt(width.W))
    val in1 = Input(UInt(width.W))
    val sel = Input(Bool())
    val out = Output(UInt(width.W))
  })

  io.out := Mux(io.sel, io.in1, io.in0)
}

class Add16 extends Module {
  val io = IO(new Bundle {
    val a    = Input(UInt(16.W))
    val b    = Input(UInt(16.W))
    val cin  = Input(Bool())
    val sum  = Output(UInt(16.W))
    val cout = Output(Bool())
  })

  io.sum  := io.a +& io.b +& io.cin
  io.cout := (io.a & io.b) | (io.a & io.cin) | (io.b & io.cin)

  // 内部逻辑
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

  val loCout    = Wire(Bool())
  val loSum     = Wire(UInt(16.W))
  val hiCout_c0 = Wire(Bool())
  val hiCout_c1 = Wire(Bool())
  val hiSum_c0  = Wire(UInt(16.W))
  val hiSum_c1  = Wire(UInt(16.W))
  val hiSum     = Wire(UInt(16.W))

  val add16_0 = Module(new Add16)
  add16_0.io.a   := io.a(15, 0)
  add16_0.io.b   := io.b(15, 0)
  add16_0.io.cin := false.B
  loCout         := add16_0.io.cout
  loSum          := add16_0.io.sum

  val add16_1_c0 = Module(new Add16)
  add16_1_c0.io.a   := io.a(31, 16)
  add16_1_c0.io.b   := io.b(31, 16)
  add16_1_c0.io.cin := false.B
  hiCout_c0         := add16_1_c0.io.cout
  hiSum_c0          := add16_1_c0.io.sum

  val add16_1_c1 = Module(new Add16)
  add16_1_c1.io.a   := io.a(31, 16)
  add16_1_c1.io.b   := io.b(31, 16)
  add16_1_c1.io.cin := true.B
  hiCout_c1         := add16_1_c1.io.cout
  hiSum_c1          := add16_1_c1.io.sum

  val u_mux2to1 = Module(new Mux2to1(16))
  u_mux2to1.io.in0 := hiSum_c0
  u_mux2to1.io.in1 := hiSum_c1
  u_mux2to1.io.sel := loCout
  hiSum            := u_mux2to1.io.out

  io.sum := Cat(hiSum, loSum)

}
