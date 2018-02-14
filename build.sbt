val commonsVersion = "0.7.5"

lazy val commonSchemas = "ch.epfl.bluebrain.nexus" %% "commons-schemas" % commonsVersion

lazy val prov = project
  .in(file("modules/prov"))
  .enablePlugins(WorkbenchPlugin)
  .disablePlugins(ScapegoatSbtPlugin, DocumentationPlugin)
  .dependsOn(provshapes)
  .settings(common)
  .settings(
    name                := "nexus-prov",
    moduleName          := "nexus-prov"
  )

lazy val provshapes = project
  .in(file("modules/provsh"))
  .enablePlugins(WorkbenchPlugin)
  .disablePlugins(ScapegoatSbtPlugin, DocumentationPlugin)
  .settings(common)
  .settings(
    name                := "nexus-prov-shapes",
    moduleName          := "nexus-prov-shapes",
    libraryDependencies += commonSchemas
  )


lazy val root = project
  .in(file("."))
  .settings(common, noPublish)
  .aggregate(prov,provshapes)

lazy val noPublish = Seq(
  publishLocal    := {},
  publish         := {},
  publishArtifact := false
)

lazy val common = Seq(
  scalacOptions in (Compile, console) ~= (_ filterNot (_ == "-Xfatal-warnings")),
  autoScalaLibrary   := false,
  workbenchVersion   := "0.2.2",
  bintrayOmitLicense := true,
  homepage           := Some(url("https://github.com/BlueBrain/nexus-prov")),
  licenses           := Seq("CC-4.0" -> url("https://github.com/BlueBrain/nexus-prov/blob/master/LICENSE")),
  scmInfo := Some(
    ScmInfo(url("https://github.com/BlueBrain/nexus-prov"), "scm:git:git@github.com:BlueBrain/nexus-prov.git"))
)

addCommandAlias("review", ";clean;test")
addCommandAlias("rel", ";release with-defaults skip-tests")
