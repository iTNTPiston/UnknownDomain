package unknowndomain.engine.client.rendering.world.chunk;

import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.lwjgl.opengl.GL11;
import unknowndomain.engine.client.rendering.shader.Shader;
import unknowndomain.engine.client.rendering.util.VertexBufferObject;
import unknowndomain.engine.util.Disposable;
import unknowndomain.engine.world.chunk.Chunk;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;

import static unknowndomain.engine.world.chunk.Chunk.CHUNK_BLOCK_POS_BIT;

public class ChunkMesh implements Disposable {

    private AtomicInteger changeCount = new AtomicInteger(0);

    private VertexBufferObject chunkSolidVbo;

    private final Chunk chunk;
    private final Vector3fc min, max;

    public ChunkMesh(Chunk chunk) {
        this.chunk = chunk;
        this.min = new Vector3f(chunk.getChunkX() << CHUNK_BLOCK_POS_BIT, chunk.getChunkY() << CHUNK_BLOCK_POS_BIT, chunk.getChunkZ() << CHUNK_BLOCK_POS_BIT);
        this.max = new Vector3f(min).add(16, 16, 16);
    }

    public void upload(ByteBuffer buffer, int vertexCount) {
        if (chunkSolidVbo == null) {
            chunkSolidVbo = new VertexBufferObject();
        }
        chunkSolidVbo.uploadData(buffer, vertexCount);
    }

    public void render() {
        if (chunk.isAirChunk()) {
            return;
        }

        if (chunkSolidVbo == null) {
            return;
        }

        chunkSolidVbo.bind();
        Shader.pointVertexAttribute(0, 3, 36, 0);
        Shader.enableVertexAttrib(0);
        Shader.pointVertexAttribute(1, 4, 36, 12);
        Shader.enableVertexAttrib(1);
        Shader.pointVertexAttribute(2, 2, 36, 28);
        Shader.enableVertexAttrib(2);
        chunkSolidVbo.drawArrays(GL11.GL_TRIANGLES);
        chunkSolidVbo.unbind();
    }

    public Chunk getChunk() {
        return chunk;
    }

    public void markDirty() {
        changeCount.getAndIncrement();
    }

    public boolean isDirty() {
        return changeCount.get() != 0;
    }

    public void startBake() {
        changeCount.set(0);
    }

    public Vector3fc getMin() {
        return min;
    }

    public Vector3fc getMax() {
        return max;
    }

    @Override
    public void dispose() {
        chunkSolidVbo.dispose();
    }
}
