package org.scalameter.scalacheck

import org.scalameter.Gen
import org.scalacheck.{Gen => SGen}

object RandomGen {

  trait Collections {
    def sizes: Gen[Int]

    /* sequences */

    def random: Gen[(Int, SGen[Int])] = for {
      size <- sizes
      randomGen = SGen.choose(0, size)
    } yield (size, randomGen)

    def randomArrays: Gen[Array[Int]] = for {
      (size, r) <- random
      array <- SGen.containerOfN[Array, Int](size, r)
    } yield array

    def randomLists: Gen[List[Int]] = for {
      (size, r) <- random
      list <- SGen.containerOfN[List, Int](size, r)
    } yield list
  }

}