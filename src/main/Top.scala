package top

import chisel3._
import _root_.circt.stage.ChiselStage
import chisel3.util.Cat

/** Top module
  */
class Top extends Module {
  val io = IO(new Bundle {

// start
    val in  = Input(UInt(8.W))
    val out = Output(UInt(8.W))

  })

  io.out := Cat(io.in(0), io.in(1), io.in(2), io.in(3), io.in(4), io.in(5), io.in(6), io.in(7))

}
