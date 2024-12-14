package top

import chisel3._
import _root_.circt.stage.ChiselStage

class mod_a extends Module {
  val io = IO(new Bundle {
    val in1 = Input(Bool())
    val in2 = Input(Bool())
    val out = Output(Bool())
  })

  // 子模块逻辑（假设是 AND 门，仅作说明）
  io.out := io.in1 & io.in2
}

/** Top module
  */
class Top extends Module {
  val io = IO(new Bundle {

// start
    val a   = Input(Bool())
    val b   = Input(Bool())
    val out = Output(Bool())

  })

  val u_mode_a = Module(new mod_a)

  u_mode_a.io.in1 := io.a
  u_mode_a.io.in2 := io.b
  io.out          := u_mode_a.io.out

}
