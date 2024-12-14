package top

import chisel3._
import _root_.circt.stage.ChiselStage
import chisel3.util.Cat

/** Top module
  */
class Top extends Module {
  val io = IO(new Bundle {

// start
    val a = Input(UInt(5.W))
    val b = Input(UInt(5.W))
    val c = Input(UInt(5.W))
    val d = Input(UInt(5.W))
    val e = Input(UInt(5.W))
    val f = Input(UInt(5.W))
    val w = Output(UInt(8.W))
    val x = Output(UInt(8.W))
    val y = Output(UInt(8.W))
    val z = Output(UInt(8.W))

  })

  val connected = Cat(io.a, io.b, io.c, io.d, io.e, io.f, 1.U(1.W), 1.U(1.W))

  io.w := connected(31, 24)
  io.x := connected(23, 16)
  io.y := connected(15, 8)
  io.z := connected(7, 0)

}
