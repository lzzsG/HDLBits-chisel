package top

import chisel3._
import _root_.circt.stage.ChiselStage
import chisel3.util.Cat
import chisel3.util.Fill

/** Top module
  */
class Top extends Module {
  val io = IO(new Bundle {

// start
    val in  = Input(UInt(8.W))
    val out = Output(UInt(32.W))

  })

  io.out := Cat(Fill(24, io.in(7)), io.in)

}
