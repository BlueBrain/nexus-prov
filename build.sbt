val commonsVersion = "0.10.8"

lazy val commonSchemas = "ch.epfl.bluebrain.nexus" %% "commons-schemas" % commonsVersion

lazy val prov = project
  .in(file("modules/prov"))
  .enablePlugins(WorkbenchPlugin)
  .disablePlugins(ScapegoatSbtPlugin, DocumentationPlugin)
  .dependsOn(provshapes)
  .settings(
    name       := "nexus-prov",
    moduleName := "nexus-prov"
  )

lazy val provshapes = project
  .in(file("modules/provsh"))
  .enablePlugins(WorkbenchPlugin)
  .disablePlugins(ScapegoatSbtPlugin, DocumentationPlugin)
  .settings(
    name                := "nexus-prov-shapes",
    moduleName          := "nexus-prov-shapes",
    libraryDependencies += commonSchemas
  )

lazy val root = project
  .in(file("."))
  .settings(noPublish)
  .aggregate(prov, provshapes)

lazy val noPublish = Seq(
  publishLocal    := {},
  publish         := {},
  publishArtifact := false
)

inThisBuild(
  Seq(
    autoScalaLibrary   := false,
    workbenchVersion   := "0.3.0",
    resolvers          += Resolver.bintrayRepo("bogdanromanx", "maven"),
    bintrayOmitLicense := true,
    homepage           := Some(url("https://github.com/BlueBrain/nexus-prov")),
    licenses           := Seq("CC-4.0" -> url("https://github.com/BlueBrain/nexus-prov/blob/master/LICENSE")),
    scmInfo := Some(
      ScmInfo(url("https://github.com/BlueBrain/nexus-prov"), "scm:git:git@github.com:BlueBrain/nexus-prov.git")),
    developers := List(Developer("MFSY", "Mohameth François Sy", "noreply@epfl.ch", url("https://bluebrain.epfl.ch/"))),
    // These are the sbt-release-early settings to configure
    releaseEarlyWith              := BintrayPublisher,
    releaseEarlyNoGpg             := true,
    releaseEarlyEnableSyncToMaven := false
  )
)

addCommandAlias("review", ";clean;test")
