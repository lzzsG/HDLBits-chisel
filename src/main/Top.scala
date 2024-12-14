package top

import chisel3._
import _root_.circt.stage.ChiselStage
import chisel3.util.Fill
import chisel3.util.Cat

/** Top module
  */
class Top extends Module {
  val io = IO(new Bundle {

// start
    val a   = Input(Bool())
    val b   = Input(Bool())
    val c   = Input(Bool())
    val d   = Input(Bool())
    val e   = Input(Bool())
    val out = Output(UInt(25.W))

  })

  val repeated_mix  = Fill(5, Cat(io.a, io.b, io.c, io.d, io.e))
  val repeated_same = Cat(Fill(5, io.a), Fill(5, io.b), Fill(5, io.c), Fill(5, io.d), Fill(5, io.e))

  io.out := ~(repeated_mix ^ repeated_same)

}
