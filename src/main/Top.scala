package top

import chisel3._
import _root_.circt.stage.ChiselStage

/** Top module
  */
class Top extends Module {
  val io = IO(new Bundle {

// start
    val in      = Input(UInt(4.W))
    val out_and = Output(Bool())
    val out_or  = Output(Bool())
    val out_xor = Output(Bool())

  })

  io.out_and := io.in.andR
  io.out_or  := io.in.orR
  io.out_xor := io.in.xorR

}
