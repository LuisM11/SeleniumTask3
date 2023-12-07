## Versions 
* Firefox 115.5.0
* Chrome 119.0.6045.199
* Selenium 4.15.0


> [!NOTE]
> * To switch between browsers, change 'browser' attribute in context.properties file. Only chrome AdBlocker works.
> * It is possible to run this without adblockers by commenting out the browser configuration code lines in WebDriversFactory

> [!CAUTION]
> Some errors or bugs appear when executing the project.
> *  An exception is always thrown at the end of the program  with Firefox (it doesn't interfer with the script or test)
>   ```
>   Dec 07, 2023 1:05:28 AM org.openqa.selenium.os.ExternalProcess$Builder lambda$start$0
>    WARNING: failed to copy the output of process 12322
>    java.io.IOException: Stream closed
>   
>   ```
> * To avoid unpredictable ads, an adblocker was used for each browser, although it generates two drawbacks: **SOLVED**
>   - By executing tests one additional browser windows is launched with two tabs 
>   - One adblocker's additional tab is opened along with the expected tab of the script, but it can be closed manually without interfereing the test.
> * Browser window doesn't close when the test finishes (only when unit test is run), but if  main method **test** is used instead the program behaves as expected and the windows close succesfuly **SOLVED**



