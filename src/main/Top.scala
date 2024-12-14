package top

import chisel3._
import _root_.circt.stage.ChiselStage
import chisel3.util.Cat

/** Top module
  */
class Top extends Module {
  val io = IO(new Bundle {

// start
    val a              = Input(UInt(3.W))
    val b              = Input(UInt(3.W))
    val out_or_bitwise = Output(UInt(3.W))
    val out_or_logical = Output(Bool())
    val out_not        = Output(UInt(6.W))

  })

  io.out_or_bitwise := io.a | io.b
  io.out_or_logical := io.a.orR || io.b.orR
  io.out_not        := Cat(~io.b, ~io.a)

}
