lazy val prov = project
  .in(file("modules/prov"))
  .enablePlugins(WorkbenchPlugin)
  .disablePlugins(ScapegoatSbtPlugin, DocumentationPlugin)
  .settings(
    name := "nexus-prov",
    moduleName := "nexus-prov",
    resolvers += Resolver.bintrayRepo("bogdanromanx", "maven"),
    autoScalaLibrary := false,
    workbenchVersion := "0.2.0"
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

addCommandAlias("review", ";clean;test")
addCommandAlias("rel", ";release with-defaults skip-tests")
