package top

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

class add16 extends Module {
  val io     = IO(new Bundle {
    val a    = Input(UInt(16.W))
    val b    = Input(UInt(16.W))
    val cin  = Input(Bool())
    val sum  = Output(UInt(16.W))
    val cout = Output(Bool())
  })
  val result = io.a +& io.b +& io.cin
  io.sum  := result(15, 0)
  io.cout := result(16)
}

/** Top module
  */
class Top extends Module {
  val io = IO(new Bundle {
// start
    val a   = Input(UInt(32.W))
    val b   = Input(UInt(32.W))
    val sub = Input(Bool())
    val sum = Output(UInt(32.W))
  })

  val inverted_b = io.b ^ Fill(32, io.sub)
  val loSum      = Wire(UInt(16.W))
  val hiSum      = Wire(UInt(16.W))

  val adder0 = Module(new add16)
  adder0.io.a   := io.a(15, 0)
  adder0.io.b   := inverted_b(15, 0)
  adder0.io.cin := io.sub
  loSum         := adder0.io.sum

  val adder1 = Module(new add16)
  adder1.io.a   := io.a(31, 16)
  adder1.io.b   := inverted_b(31, 16)
  adder1.io.cin := adder0.io.cout
  hiSum         := adder1.io.sum

  io.sum := Cat(hiSum, loSum)

}
