package unknowndomain.engine.client.rendering.block.model;

import unknowndomain.engine.client.rendering.texture.TextureUV;
import unknowndomain.engine.util.Facing;

public class BlockModelQuad {

    public float[] vertexs;
    public Facing facing;
    public TextureUV textureUV;

    public BlockModelQuad(float[] vertexs, Facing facing, TextureUV textureUV) {
        this.vertexs = vertexs;
        this.facing = facing;
        this.textureUV = textureUV;
    }

    public static BlockModelQuad createQuad(float fromX, float fromY, float toX, float toY, float z, Facing facing, TextureUV textureUV) {
        switch (facing) {
            case NORTH:
                return new BlockModelQuad(new float[]{fromX, fromY, z, toX, fromY, z, toX, toY, z, fromX, toY, z}, facing, textureUV);
            case SOUTH:
                return new BlockModelQuad(new float[]{toX, fromY, z, fromX, fromY, z, fromX, toY, z, toX, toY, z}, facing, textureUV);
            case EAST:
                return new BlockModelQuad(new float[]{z, fromX, toY, z, fromX, fromY, z, toX, fromY, z, toX, toY}, facing, textureUV);
            case WEST:
                return new BlockModelQuad(new float[]{z, fromX, fromY, z, fromX, toY, z, toX, toY, z, toX, fromY}, facing, textureUV);
            case TOP:
                return new BlockModelQuad(new float[]{fromX, z, toY, toX, z, toY, toX, z, fromY, fromX, z, fromY}, facing, textureUV);
            case BOTTOM:
                return new BlockModelQuad(new float[]{toX, z, toY, fromX, z, toY, fromX, z, fromY, toX, z, fromY}, facing, textureUV);
            default:
                throw new IllegalArgumentException();
        }
    }
}
