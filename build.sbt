val commonsVersion = "0.5.30"

lazy val commonSchemas = "ch.epfl.bluebrain.nexus" %% "commons-schemas" % commonsVersion

lazy val prov = project
  .in(file("modules/prov"))
  .enablePlugins(WorkbenchPlugin)
  .disablePlugins(ScapegoatSbtPlugin, DocumentationPlugin)
  .settings(common)
  .settings(
    name                := "nexus-prov",
    moduleName          := "nexus-prov",
    autoScalaLibrary    := false,
    workbenchVersion    := "0.2.2",
    libraryDependencies += commonSchemas
  )

lazy val root = project
  .in(file("."))
  .settings(common, noPublish)
  .aggregate(prov)

lazy val noPublish = Seq(
  publishLocal    := {},
  publish         := {},
  publishArtifact := false
)

lazy val common = Seq(
  bintrayOmitLicense := true,
  homepage           := Some(url("https://github.com/BlueBrain/nexus-prov")),
  licenses           := Seq("CC-4.0" -> url("https://github.com/BlueBrain/nexus-prov/blob/master/LICENSE")),
  scmInfo := Some(
    ScmInfo(url("https://github.com/BlueBrain/nexus-prov"), "scm:git:git@github.com:BlueBrain/nexus-prov.git"))
)

addCommandAlias("review", ";clean;test")
addCommandAlias("rel", ";release with-defaults skip-tests")
