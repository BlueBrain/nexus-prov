lazy val commonsVersion   = "0.4.3"
lazy val scalaTestVersion = "3.0.4"
lazy val akkaHttpVersion  = "10.0.10"

lazy val shaclValidator = "ch.epfl.bluebrain.nexus" %% "shacl-validator" % commonsVersion
lazy val akkaHttpCore   = "com.typesafe.akka"       %% "akka-http-core"  % akkaHttpVersion
lazy val scalaTest      = "org.scalatest"           %% "scalatest"       % scalaTestVersion

val baseUri = "http://localhost/v0"

lazy val workbench = project
  .in(file("modules/workbench"))
  .settings(commonSettings, noPublishSettings)
  .settings(name := "schema-workbench",
            moduleName := "schema-workbench",
            libraryDependencies ++= Seq(shaclValidator, akkaHttpCore, scalaTest))

lazy val prov = project
  .in(file("modules/prov"))
  .enablePlugins(BuildInfoPlugin)
  .dependsOn(workbench % Test)
  .settings(commonSettings, buildInfoSettings)
  .settings(name := "nexus-prov", moduleName := "nexus-prov", libraryDependencies ++= Seq(scalaTest % Test))

lazy val root = project
  .in(file("."))
  .settings(name := "nexus-prov-root", moduleName := "nexus-prov-root")
  .settings(commonSettings, noPublishSettings)
  .aggregate(workbench, prov)

lazy val buildInfoSettings = Seq(
  buildInfoKeys := Seq[BuildInfoKey](
    BuildInfoKey("base" -> baseUri),
    BuildInfoKey.map(resources.in(Compile)) {
      case (_, v) =>
        val resourceBase = resourceDirectory.in(Compile).value.getAbsolutePath
        val dirsWithJson = (v * "schemas" ** "*.json").get
        val schemas      = dirsWithJson.map(_.getAbsolutePath.substring(resourceBase.length))
        "schemas" -> schemas
    },
    BuildInfoKey.map(resources.in(Compile)) {
      case (_, v) =>
        val resourceBase = resourceDirectory.in(Compile).value.getAbsolutePath
        val dirsWithJson = (v * "contexts" ** "*.json").get
        val contexts     = dirsWithJson.map(_.getAbsolutePath.substring(resourceBase.length))
        "contexts" -> contexts
    },
    BuildInfoKey.map(resources.in(Test)) {
      case (_, v) =>
        val resourceBase = resourceDirectory.in(Test).value.getAbsolutePath
        val dirsWithJson = (v * "data" ** "*.json").get
        val data         = dirsWithJson.map(_.getAbsolutePath.substring(resourceBase.length))
        "data" -> data
    },
    BuildInfoKey.map(resources.in(Test)) {
      case (_, v) =>
        val resourceBase = resourceDirectory.in(Test).value.getAbsolutePath
        val dirsWithJson = (v * "invalid" ** "*.json").get
        val invalid      = dirsWithJson.map(_.getAbsolutePath.substring(resourceBase.length))
        "invalid" -> invalid
    }
  ),
  buildInfoPackage := "ch.epfl.bluebrain.nexus.prov"
)

lazy val commonSettings = Seq(resolvers += Resolver.bintrayRepo("bogdanromanx", "maven"))

lazy val noPublishSettings = Seq(publishLocal := {}, publish := {}, publishArtifact := false)

def nexusDep(name: String, version: String): ModuleID =
  "ch.epfl.bluebrain.nexus" %% name % version

addCommandAlias("review", ";clean;test")
addCommandAlias("rel", ";release with-defaults skip-tests")
