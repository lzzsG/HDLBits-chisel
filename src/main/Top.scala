package top

import chisel3._
import _root_.circt.stage.ChiselStage

class mod_a extends BlackBox {
  val io = IO(new Bundle {
    val out1 = Output(Bool())
    val out2 = Output(Bool())
    val a    = Input(Bool())
    val b    = Input(Bool())
    val c    = Input(Bool())
    val d    = Input(Bool())
  })
}

/** Top module
  */
class Top extends Module {
  val io = IO(new Bundle {

// start
    val a    = Input(Bool())
    val b    = Input(Bool())
    val c    = Input(Bool())
    val d    = Input(Bool())
    val out1 = Output(Bool())
    val out2 = Output(Bool())

  })

  val u_mod_a = Module(new mod_a)

  io.out1      := u_mod_a.io.out1
  io.out2      := u_mod_a.io.out2
  u_mod_a.io.a := io.a
  u_mod_a.io.b := io.b
  u_mod_a.io.c := io.c
  u_mod_a.io.d := io.d

}
