package top

import chisel3._
import _root_.circt.stage.ChiselStage
import chisel3.util.MuxLookup

class my_diff8 extends Module {
  val io = IO(new Bundle {
    val clk = Input(Clock())
    val d   = Input(UInt(8.W))
    val q   = Output(UInt(8.W))
  })

  val q = RegNext(io.d, 0.U)
  io.q := q
}

/** Top module
  */
class Top extends Module {
  val io = IO(new Bundle {

// start
    val clk = Input(Clock())
    val sel = Input(UInt(2.W))
    val d   = Input(UInt(8.W))
    val q   = Output(UInt(8.W))

  })

  val q1 = Wire(UInt(8.W))
  val q2 = Wire(UInt(8.W))
  val q3 = Wire(UInt(8.W))

  val u_diff1 = Module(new my_diff8)
  val u_diff2 = Module(new my_diff8)
  val u_diff3 = Module(new my_diff8)

  u_diff1.io.clk := io.clk
  u_diff1.io.d   := io.d
  q1             := u_diff1.io.q

  u_diff2.io.clk := io.clk
  u_diff2.io.d   := q1
  q2             := u_diff2.io.q

  u_diff3.io.clk := io.clk
  u_diff3.io.d   := q2
  q3             := u_diff3.io.q

  io.q := MuxLookup(io.sel, io.d)(
    Seq(
      0.U -> io.d,
      1.U -> q1,
      2.U -> q2,
      3.U -> q3
    )
  )

}
