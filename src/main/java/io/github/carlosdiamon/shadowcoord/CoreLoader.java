package io.github.carlosdiamon.shadowcoord;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({ "UnstableApiUsage", "unused" })
public class CoreLoader
	implements PluginLoader {

	@Override
	public void classloader(@NotNull final PluginClasspathBuilder classpathBuilder) {
		final var resolver = new MavenLibraryResolver();

		final var mavenCentral = new RemoteRepository
			                             .Builder("central",
		                                        "default", "https://repo1.maven.org/maven2/")
			                         .build();

		final var configurate = new Dependency(
			new DefaultArtifact("org.spongepowered:configurate-hocon:4.1.2"),
			null
		);
		resolver.addRepository(mavenCentral);
		resolver.addDependency(configurate);
		classpathBuilder.addLibrary(resolver);
	}
}
