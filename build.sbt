import sbt.ModuleID

val commonsVersion         = "0.5.20"

lazy val commonSchemas = nexusDep("commons-schemas", commonsVersion)

lazy val prov = project
  .in(file("modules/prov"))
  .enablePlugins(WorkbenchPlugin)
  .disablePlugins(ScapegoatSbtPlugin, DocumentationPlugin)
  .settings(
    name := "nexus-prov",
    moduleName := "nexus-prov",
    resolvers += Resolver.bintrayRepo("bogdanromanx", "maven"),
    autoScalaLibrary := false,
    workbenchVersion := "0.2.0",
    libraryDependencies += commonSchemas
  )

lazy val root = project
  .in(file("."))
  .settings(noPublish)
  .aggregate(prov)

lazy val noPublish = Seq(
  publishLocal := {},
  publish := {},
  publishArtifact := false
)

def nexusDep(name: String, version: String): ModuleID =
  "ch.epfl.bluebrain.nexus" %% name % version

addCommandAlias("review", ";clean;test")
addCommandAlias("rel", ";release with-defaults skip-tests")
