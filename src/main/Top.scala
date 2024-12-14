package top

import chisel3._
import _root_.circt.stage.ChiselStage
import chisel3.util.Cat

/** Top module
  */
class Top extends Module {
  val io = IO(new Bundle {

// start
    val in  = Input(UInt(32.W))
    val out = Output(UInt(32.W))

  })

  io.out := Cat(io.in(7, 0), io.in(15, 8), io.in(23, 16), io.in(31, 24))

}
