# Configuring Checkstyle for Eclipse #

1. Ensure that you have setup the plugin as per [CheckstyleForEclipse](CheckstyleForEclipse.md)

2. We need to generate a checkstyle configuration file for eclipse. If a file named `checkstyle-main-eclipse.xml` exists in the `installation_dir/core/etc/checkstyle/` directory, then delete it. Open command prompt and run
```
./build.sh setup-eclipse
```
on Linux or

```
build.cm setup-eclipse
```
on Windows.

from the `installation_dir/core` directory. This will create a file called `checkstyle-main-eclipse.xml` within `installation_dir/core/etc/checkstyle` directory.

3. Select **Window**->**Preferences...**->**Team**->**Ignored Resources**. Click **Add Pattern...** and enter `checkstyle-main-eclipse.xml`. Click **OK**. Click **Apply**.

![http://instinct.googlecode.com/svn/wiki/images/Eclipse_CS_Ignored_Resources.png](http://instinct.googlecode.com/svn/wiki/images/Eclipse_CS_Ignored_Resources.png)

4. Select **Window**->**Preferences...**->**Checkstyle**. The default configuration should be as follows:

![http://instinct.googlecode.com/svn/wiki/images/Eclipse_CS_Plugin.png](http://instinct.googlecode.com/svn/wiki/images/Eclipse_CS_Plugin.png)

5. Click **New..**.

![http://instinct.googlecode.com/svn/wiki/images/Eclipse_CS_Check_Configuration_Properties.png](http://instinct.googlecode.com/svn/wiki/images/Eclipse_CS_Check_Configuration_Properties.png)

6. Choose **Internal Configuration** for "Type" and enter "Instinct Checks" for both "Name and "Description" fields (or anything your heart desires).

7. Click the **Import...** button down the bottom and browse to the `installation_dir/core/etc/checkstyle/checkstyle-main-eclipse.xml` file.

8. Click **OK** twice. Your configuration should look something like:

![http://instinct.googlecode.com/svn/wiki/images/Eclipse_CS_Preferences_With_Instinct_Checks.png](http://instinct.googlecode.com/svn/wiki/images/Eclipse_CS_Preferences_With_Instinct_Checks.png)


9. Select **eclipse-instinct-core** in the Package tree. Press **Alt+Enter** to display the project properties window. Choose **Checkstyle**. Your configuration for Checkstyle should look like the following:

![http://instinct.googlecode.com/svn/wiki/images/Eclipse_CS_Final_Checks_FS.png](http://instinct.googlecode.com/svn/wiki/images/Eclipse_CS_Final_Checks_FS.png)

10. Click **OK**.

11. Choose **Window**->**Show View**->**Other...**->**Checkstyle**->**Checkstyle violations**.

![http://instinct.googlecode.com/svn/wiki/images/Eclipse_Checkstyle_Show_View.png](http://instinct.googlecode.com/svn/wiki/images/Eclipse_Checkstyle_Show_View.png)

12. This will display any Checkstyle errors in its own tab:

![http://instinct.googlecode.com/svn/wiki/images/Eclipse_Checkstyle_Sample_Error.png](http://instinct.googlecode.com/svn/wiki/images/Eclipse_Checkstyle_Sample_Error.png)

13. Double-clicking on a Checkstyle error will take you to a detailed view of the error. Double-clicking on a detail will take you straight to the code that violates the checker.

![http://instinct.googlecode.com/svn/wiki/images/Eclipse_CS_Checkstyle_Details.png](http://instinct.googlecode.com/svn/wiki/images/Eclipse_CS_Checkstyle_Details.png)

If you want to force a check on some source, right-click on **eclipse-instinct-core**->**Checkstyle** and choose **Check Code with Checkstyle**.

![http://instinct.googlecode.com/svn/wiki/images/Eclipse_Checkstyle_Run_Code_Check.png](http://instinct.googlecode.com/svn/wiki/images/Eclipse_Checkstyle_Run_Code_Check.png)

14. Repeat steps 8 on the **eclipse-instinct-example** project to verify that Checkstyle has been set.