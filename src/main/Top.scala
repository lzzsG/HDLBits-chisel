package top

import chisel3._
import _root_.circt.stage.ChiselStage
import chisel3.util.Cat

class add16 extends Module {
  val io = IO(new Bundle {
    val a    = Input(UInt(16.W))
    val b    = Input(UInt(16.W))
    val cin  = Input(UInt(1.W))
    val cout = Output(UInt(1.W))
    val sum  = Output(UInt(16.W))
  })

  val result = Wire(UInt(17.W))
  result  := io.a + io.b + io.cin
  io.sum  := result(15, 0)
  io.cout := result(16)
}

/** Top module
  */
class Top extends Module {
  val io = IO(new Bundle {

// start
    val a   = Input(UInt(32.W))
    val b   = Input(UInt(32.W))
    val sum = Output(UInt(32.W))
  })

  val loCout = Wire(UInt(1.W))

  val add16_0 = Module(new add16)
  add16_0.io.a   := io.a(15, 0)
  add16_0.io.b   := io.b(15, 0)
  add16_0.io.cin := false.B
  loCout         := add16_0.io.cout

  val add16_1 = Module(new add16)
  add16_1.io.a   := io.a(31, 16)
  add16_1.io.b   := io.b(31, 16)
  add16_1.io.cin := loCout

  io.sum := Cat(add16_1.io.sum, add16_0.io.sum)

}
