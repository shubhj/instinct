# Instinct Release Instructions #

Step 1. Automate.

Step 2. If you can't, do the following (make sure you're on Java 1.5).

  1. Ensure all module RELEASE-NOTES are up to date.
  1. Build the core project: $./build.sh
  1. Build the example project: $./build.sh
  1. Build the IDEA plugin.
  1. Commit any outstanding changes (all modules).
  1. Make a tag for the release in subversion (see command below).
  1. Upload the core zip to Google Code, labels: Type-Archive, OpSys-All, Featured
  1. Upload the example zip to Google Code, labels: Type-Archive, OpSys-All, Featured
  1. Upload the idea zip to Google Code.
  1. Upload the idea zip to IntelliJ plugins repository: http://plugins.intellij.net/plugin/add
  1. Change the featured download from the old releases to the new ones.
  1. Update the front page with the details on the new version.
  1. Post to the general list.
  1. Post to Tom's blog.
  1. Post to testdriven.com
  1. Update the version number in core/build-setup.xml, example/build.xml, core/pom.xml and example/pom.xml.
  1. Build the core and copy the new JAR into the example project.
  1. svn add the new JAR, svn remove the old one.
  1. Build example project, check in if all's well.

Example tag command:
```
$  svn copy -m 'Release 0.1.3' https://instinct.googlecode.com/svn/trunk https://instinct.googlecode.com/svn/tags/Release-0.1.3
```