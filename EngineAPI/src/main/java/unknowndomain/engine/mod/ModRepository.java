package unknowndomain.engine.mod;

import unknowndomain.engine.Engine;
import unknowndomain.engine.util.Owner;
import unknowndomain.engine.util.versioning.ComparableVersion;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * The repo of the mod, can be maven, ftp or any other form
 */
@Owner(Engine.class)
public interface ModRepository {
    Collection<RemoteModMetadata> fetchIndex();

    InputStream open(ModIdentifier identifier);

    boolean contains(ModIdentifier identifier);

    class RemoteModMetadata extends ModMetadata {
        private String sha256;
        private long size;

        protected RemoteModMetadata(String group, String id, ComparableVersion version, String name, String description, String url, List<String> authors, String logoFile, List<ModDependencyEntry> dependencies, Map<String, Object> properties, String sha256, long size) {
            super(group, id, version, name, description, url, authors, logoFile, dependencies, properties);
            this.sha256 = sha256;
            this.size = size;
        }

        public long getSize() {
            return size;
        }

        public String getSha256() {
            return sha256;
        }

        public static class Builder extends ModMetadata.Builder {
            private String sha256;
            private long size;


            public Builder setSha256(String sha256) {
                this.sha256 = sha256;
                return this;
            }

            public Builder setSize(long size) {
                this.size = size;
                return this;
            }

            @Override
            public RemoteModMetadata build() {
                return new RemoteModMetadata(group, id, version, name, description, url, authors, logo, dependency, properties, sha256, size);
            }
        }
    }
}
