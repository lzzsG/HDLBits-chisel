package top

import chisel3._
import _root_.circt.stage.ChiselStage

class my_dff extends Module {
  val io = IO(new Bundle {
    val clk = Input(Bool())
    val d   = Input(Bool())
    val q   = Output(Bool())
  })

  // D触发器实现，简单逻辑为存储单元
  val reg = RegNext(io.d)
  io.q := reg
}

/** Top module
  */
class Top extends Module {
  val io = IO(new Bundle {

// start
    val clk = Input(Bool())
    val d   = Input(Bool())
    val q   = Output(Bool())

  })

  val q1 = Wire(Bool())
  val q2 = Wire(Bool())

  val u_dff1 = Module(new my_dff)
  u_dff1.io.clk := io.clk
  u_dff1.io.d   := io.d
  q1            := u_dff1.io.q

  val u_dff2 = Module(new my_dff)
  u_dff2.io.clk := io.clk
  u_dff2.io.d   := q1
  q2            := u_dff2.io.q

  val u_dff3 = Module(new my_dff)
  u_dff3.io.clk := io.clk
  u_dff3.io.d   := q2
  io.q          := u_dff3.io.q
}
