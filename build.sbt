name          := "scala-aws-lambda-utils"
organization  := "com.github.yeghishe"
version       := "0.0.1"
scalaVersion  := "2.11.8"
scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8")

resolvers += Resolver.jcenterRepo

libraryDependencies ++= {
  val scalaTestV = "3.0.0"
  val scalaMockV = "3.2.2"
  val circeV     = "0.5.1"
  val awsLambdaV = "1.1.0"
  Seq(
    "io.circe"      %% "circe-core"                  % circeV,
    "io.circe"      %% "circe-generic"               % circeV,
    "io.circe"      %% "circe-parser"                % circeV,
    "com.amazonaws"  % "aws-lambda-java-core"        % awsLambdaV,
    "com.amazonaws"  % "aws-lambda-java-log4j"       % "1.0.0",
    "org.scalatest" %% "scalatest"                   % scalaTestV % "it,test",
    "org.scalamock" %% "scalamock-scalatest-support" % scalaMockV % "it,test"
  )
}

lazy val root = project.in(file(".")).configs(IntegrationTest)
Defaults.itSettings

initialCommands := "import io.github.yeghishe.lambda._"

publishMavenStyle := true
publishArtifact in Test := false
pomIncludeRepository := { _ => false }
publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}
pomExtra := (
  <url>http://yeghishe.github.io/</url>
  <licenses>
    <license>
      <name>Apache-2.0</name>
      <url>http://opensource.org/licenses/Apache-2.0</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>https://github.com/yeghishe/</url>
    <connection>scm:git:git@github.com:yeghishe/.git</connection>
  </scm>
  <developers>
    <developer>
      <id>ypiruzyan</id>
      <name>Yeghishe Piruzyan</name>
      <url>http://yeghishe.github.io/</url>
    </developer>
  </developers>)
