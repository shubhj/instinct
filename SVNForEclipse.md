# Setting up an SVN plugin for Eclipse #
1. Launch Eclipse.

2. Click **Help**->**Software Updates**-> **Find and Install...**

3. Choose "Search for new features to install". Click **Next**.

4. Add the svn remote update site:
  * **Name:** Subclipse
  * **Url:** http://subclipse.tigris.org/update_1.2.x

5. Select the above site and Click Finish.

![http://instinct.googlecode.com/svn/wiki/images/Add_Subclipse_Remote_site.png](http://instinct.googlecode.com/svn/wiki/images/Add_Subclipse_Remote_site.png)

6. On the Updates dialog, drop down the **Subclipse**->**Subclipse Plugin**->**Subclipse 1.2.4** feature and select it. Click **Next**.

![http://instinct.googlecode.com/svn/wiki/images/Install_Subclipse.png](http://instinct.googlecode.com/svn/wiki/images/Install_Subclipse.png)

7. Accept the license agreement and click **Next**.

8. Click **Finish** on the installation summary page.

9. On the verification screen choose **Install All**.

![http://instinct.googlecode.com/svn/wiki/images/Subclipse_Verification.png](http://instinct.googlecode.com/svn/wiki/images/Subclipse_Verification.png)

10. On the **Install/Update** screen choose **Yes** to restarting Eclipse.

11. Choose **Windows**->**Preferences**->**Team**->**SVN**. If you get a "Failed to load JavaHL Library" error:

![http://instinct.googlecode.com/svn/wiki/images/JavahHL_Error.png](http://instinct.googlecode.com/svn/wiki/images/JavahHL_Error.png)

> you could either install and configure the specified libraries or choose **SVNKit (Pure Java)** from under the "SVN Interface" section and click **Apply**.

![http://instinct.googlecode.com/svn/wiki/images/Subclipse_installed.png](http://instinct.googlecode.com/svn/wiki/images/Subclipse_installed.png)

12. Right-click on the "eclipse-instinct-core' project in the package tree to access the context menu. Select **Team**->**Share Project...**

![http://instinct.googlecode.com/svn/wiki/images/Eclipse_SVN_Context_Menu_Share.png](http://instinct.googlecode.com/svn/wiki/images/Eclipse_SVN_Context_Menu_Share.png)

13. Select **SVN** from the "Share Project" dialog. Click **Next**.

![http://instinct.googlecode.com/svn/wiki/images/Eclipse_SVN_Share_Project.png](http://instinct.googlecode.com/svn/wiki/images/Eclipse_SVN_Share_Project.png)

14. Select **Validate Connection on Finish** and click **Finish**.

![http://instinct.googlecode.com/svn/wiki/images/Eclipse_SVN_Connect_To_Repository.png](http://instinct.googlecode.com/svn/wiki/images/Eclipse_SVN_Connect_To_Repository.png)

15. Enter your Instinct user name and password when prompted. Your Instinct details can be found under the [Source](http://code.google.com/p/instinct/source) tab. Check **Save Password** if you want your password to be saved.

![http://instinct.googlecode.com/svn/wiki/images/Eclipse_SVN_Authentication.png](http://instinct.googlecode.com/svn/wiki/images/Eclipse_SVN_Authentication.png)

16. Follow steps 12-15 for the example project as well.

17. You can now access the **Team** Context menu to perform functions on Subversion.

![http://instinct.googlecode.com/svn/wiki/images/Eclipse_SVN_Context_Menu.png](http://instinct.googlecode.com/svn/wiki/images/Eclipse_SVN_Context_Menu.png)