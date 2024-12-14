package top

import chisel3._
import _root_.circt.stage.ChiselStage

/** Top module
  */
class Top extends Module {
  val io = IO(new Bundle {
// start
    val a          = Input(Bool())
    val b          = Input(Bool())
    val sel_b1     = Input(Bool())
    val sel_b2     = Input(Bool())
    val out_assign = Output(Bool())
    val out_always = Output(Bool())
  })

  io.out_assign := Mux(io.sel_b1 && io.sel_b2, io.b, io.a)

  when(io.sel_b1 && io.sel_b2) {
    io.out_always := io.b
  }.otherwise {
    io.out_always := io.a
  }

}
