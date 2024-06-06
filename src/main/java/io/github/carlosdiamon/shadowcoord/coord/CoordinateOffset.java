package io.github.carlosdiamon.shadowcoord.coord;

import io.github.carlosdiamon.shadowcoord.util.RandomUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the amount by which a player's clientside X and Z coordinates will appear shifted
 * compared to their real position in a world.
 *
 * <p>An CoordinateOffset of <code>(16, 16)</code> would result in a player seeing themselves at <code>(0,
 * 0)</code> when they are standing at <code>(16, 16)</code> in the Overworld, and seeing themselves
 * standing at <code>(-16, -16)</code> when they are standing at the real origin.</p>
 *
 * @param coordX CoordinateOffset amount for the X coordinate. Must be a multiple of 16 to align with chunk boundaries.
 * @param coordZ CoordinateOffset amount for the Z coordinate. Must be a multiple of 16 to align with chunk boundaries.
 *
 * @author Joshua Prince
 * 	<a
 * 	href="https://github.com/joshuaprince/CoordinateCoordinateOffset/blob/master/src/main/java/com/jtprince/coordinateCoordinateOffset/CoordinateOffset.java#L75"> Github Code
 * 	</a>
 */
public record CoordinateOffset(
	int coordX,
	int coordZ
) {

	/**
	 * Argument for the {@code toChunksPower} parameter of {@link #align(int, int, int)} that results
	 * in an Overworld CoordinateOffset that will cleanly translate to a Nether CoordinateOffset.
	 */
	public static final int ALIGN_OVERWORLD = 3;

	public CoordinateOffset {
		if (coordX % 16 != 0 || coordZ % 16 != 0) {
			throw new IllegalArgumentException("Coordinate CoordinateOffsets must be multiples of 16.");
		}
	}

	/**
	 * Get a random CoordinateOffset, with X and Z in the range <code>(-bound, bound)</code>.
	 *
	 * @param bound
	 * 	Maximum absolute value of each CoordinateOffset component.
	 *
	 * @return A new CoordinateOffset with values that are multiples of 128 blocks.
	 */
	public static @NotNull CoordinateOffset random(int bound) {
		return align(
			RandomUtil.nextInt(-bound, bound),
			RandomUtil.nextInt(-bound, bound),
			ALIGN_OVERWORLD
		);
	}

	/**
	 * Get a new CoordinateOffset closest to the specified CoordinateOffset that is aligned to chunk borders.
	 *
	 * <p>CoordinateOffsets MUST be aligned with chunk borders, meaning each component is divisible by 16.</p>
	 *
	 * @param x X coordinate.
	 * 	X CoordinateOffset
	 * @param z Z coordinate.
	 * 	Z CoordinateOffset
	 * @param toChunksPower
	 * 	Value used to perform extra alignment with chunks. The input coordX/coordZ will be rounded to the
	 * 	nearest <code>2^toChunksPower</code> chunks. This is useful for making Nether translations
	 * 	predictable: we want Overworld CoordinateOffsets to still align to chunk boundaries even after dividing
	 * 	them by 8. Therefore, we would use {@value ALIGN_OVERWORLD} as the value here when aligning
	 * 	the Overworld CoordinateOffset (since 2^3 == 8).
	 *
	 * @return A new CoordinateOffset.
	 */
	public static @NotNull CoordinateOffset align(int x, int z, int toChunksPower) {
		int shift = toChunksPower + 4;

		// Add half of the divisor so that the output is rounded instead of just floored
		x += 1 << (shift - 1);
		z += 1 << (shift - 1);

		return new CoordinateOffset(x >> shift << shift, z >> shift << shift);
	}

	public static @NotNull CoordinateOffset align(int x, int z) {
		return align(x, z, 0);
	}

	public int chunkX() {
		return coordX >> 4;
	}

	public int chunkZ() {
		return coordZ >> 4;
	}

	/**
	 * Get a new CoordinateOffset with the inverse components as this one (coordX -> -coordX, coordZ -> -coordZ).
	 *
	 * @return A new CoordinateOffset.
	 */
	public @NotNull CoordinateOffset negate() {
		return new CoordinateOffset(-coordX, -coordZ);
	}

	@Override
	public String toString() {
		return "CoordinateCoordinateOffset{" +
		       "coordX=" + coordX +
		       ", coordZ=" + coordZ +
		       '}';
	}
}
