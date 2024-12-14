package top

import chisel3._
import _root_.circt.stage.ChiselStage

/** Top module
  */
class Top extends Module {
  val io = IO(new Bundle {
// start
    val a               = Input(Bool())
    val b               = Input(Bool())
    val out_assign      = Output(Bool())
    val out_alwaysblock = Output(Bool())
  })

  io.out_assign := io.a & io.b

  val result = Wire(Bool())
  when(io.a && io.b) {
    result := true.B
  }.otherwise {
    result := false.B
  }
  io.out_alwaysblock := result

}
