package org.scalameter

import org.scalacheck.{Gen => SGen}
import org.scalacheck.Gen.Params
import scala.collection.Iterator

package object scalacheck {
  implicit def scalacheck2scalameterGen[A](gen: SGen[A]): Gen[A] = new Gen[A] {
    def generate(params: Parameters): A = gen(params[Params](gen.label)).get

    def warmupset: Iterator[A] = Iterator.single(gen.sample.get)

    def dataset: Iterator[Parameters] = Iterator.single(Parameters(gen.label -> SGen.Params()))
  }

}