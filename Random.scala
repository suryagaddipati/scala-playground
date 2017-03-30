

trait RNG {
  def nextInt: (Int, RNG) // Should generate a random `Int`. We'll later define other functions in terms of `nextInt`.
}
//copy pasta from book
case class Simple(seed: Long) extends RNG {
  def nextInt: (Int, RNG) = {
    val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL // `&` is bitwise AND. We use the current seed to generate a new seed.
    val nextRNG = Simple(newSeed) // The next state, which is an `RNG` instance created from the new seed.
    val n = (newSeed >>> 16).toInt // `>>>` is right binary shift with zero fill. The value `n` is our new pseudo-random integer.
    (n, nextRNG) // The return value is a tuple containing both a pseudo-random integer and the next `RNG` state.
  }
}

// println(Simple(1).nextInt._1)

def nonNegativeInt(rng: RNG): (Int, RNG) = rng.nextInt match{
  case (i , nRng) if( i < 0) => (-(i + 1), nRng)
  case (i,nRng) => (i,nRng)
}

println(nonNegativeInt(Simple(1))._1)
