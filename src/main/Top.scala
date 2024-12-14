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
    val out_always_comb = Output(Bool())
    val out_always_ff   = Output(Bool())
// end
  })

  val aXorb = Wire(Bool())
  aXorb := io.a ^ io.b

  io.out_assign := aXorb

  io.out_always_comb := aXorb

  io.out_always_ff := RegNext(aXorb)

}
