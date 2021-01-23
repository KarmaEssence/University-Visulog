package up.visulog.cli;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @version 1.0
 */
public class MakeWebFiles {
    
    /**
     * calls makeFile twice and prints hyppens
     */
    public static void makeFiles(){
        System.out.println("-".repeat(19)+" HTML Creation Log "+"-".repeat(19)+"\n");
        makeFile("index.html");
        makeFile("style.css");
        System.out.println("-".repeat(57));
    }

    /**
     * allows to create a new File with the parameter
     * @param fileName the name of the File we'll have to create
     */
    private static void makeFile(String fileName){
        try {
            File myFile = new File("../"+fileName);  
            if (myFile.createNewFile()) {  
                System.out.println("File created: " + fileName);                
                if (fileName.equals("index.html")){
                    writeFile(generateHTML(), "index.html");
                } else {
                    writeFile(generateCSS(), "style.css");
                }  
            } else {  
                System.out.println("**NOTE** File '"+fileName+"' already exists.\n**NOTE** To reset the file, re-run the program with the '--overwrite' argument\n");
            }  
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();  
        }
    }

    /**
     * allows to write text into a File 
     * @param content the text which will be added to the File
     * @param fileName the name of the File which will be modified
     */
    private static void writeFile(String content, String fileName){
        try {
            FileWriter writer = new FileWriter("../"+fileName);
            writer.write(content);
            writer.close();
            System.out.println("Successfully wrote to '"+fileName+"'\n");
        } catch (IOException e) {
            System.out.println("**ERROR** An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * allows to generate CSS code 
     * @return css a String containing all the CSS code 
     */
    private static String generateCSS(){
        String css = "#chartContainer {";
        css+="\n"+"    height: 600px;";
        css+="\n"+"    width: 85%;";
        css+="\n"+"    display: block;";
        css+="\n"+"    margin-left: auto;";
        css+="\n"+"    margin-right: auto;";
        css+="\n"+"}";
        css+="\n"+"";
        css+="\n"+"#chart {";
        css+="\n"+"    padding-top: 8px;";
        css+="\n"+"    padding-bottom: 32px;";
        css+="\n"+"}";
        css+="\n"+"";
        css+="\n"+"#selector{";
        css+="\n"+"    width: 50%;";
        css+="\n"+"    display: block;";
        css+="\n"+"    padding-top: 16px;";
        css+="\n"+"    margin-left: auto;";
        css+="\n"+"    margin-right: auto;";
        css+="\n"+"    padding-bottom: 48px;";
        css+="\n"+"    font-size: 1.1em;";
        css+="\n"+"}";
        css+="\n"+"";
        css+="\n"+"#selectorText{";
        css+="\n"+"    padding-right: 24px;";
        css+="\n"+"}";
        css+="\n"+"";
        css+="\n"+"body {";
        css+="\n"+"    margin: 0;";
        css+="\n"+"    font-family: 'Noto Sans JP', sans-serif;";
        css+="\n"+"}";
        css+="\n"+"";
        css+="\n"+".header {";
        css+="\n"+"    text-align: center;";
        css+="\n"+"    font-size: 1.3em;";
        css+="\n"+"    background: #560e86;";
        css+="\n"+"    padding-top: 2px;";
        css+="\n"+"    padding-bottom: 2px;";
        css+="\n"+"    color: #f1f1f1;";
        css+="\n"+"    z-index:1;";
        css+="\n"+"}";
        css+="\n"+"";
        css+="\n"+".content {";
        css+="\n"+"    padding: 8px;";
        css+="\n"+"    padding-top: 16px;";
        css+="\n"+"    padding-bottom: 16px;";
        css+="\n"+"    z-index:3;";
        css+="\n"+"}";
        css+="\n"+"";
        css+="\n"+".footer {";
        css+="\n"+"    width: 100%;";
        css+="\n"+"    position: relative;";
        css+="\n"+"    bottom: 0;";
        css+="\n"+"    background-color: #d5d5d5;";
        css+="\n"+"    text-align: center;";
        css+="\n"+"    padding-top: 8px;";
        css+="\n"+"    padding-bottom: 8px;";
        css+="\n"+"}";
        css+="\n"+"";
        css+="\n"+"#buttonText2{";
        css+="\n"+"    background-color: #3abbbb;";
        css+="\n"+"    color: white;";
        css+="\n"+"}";
        css+="\n"+"";
        css+="\n"+"#buttonText2:hover, #buttonText2:focus {";
        css+="\n"+"    background-color: #2a8f8f;";
        css+="\n"+"}";
        css+="\n"+"";
        css+="\n"+".dropbtn {";
        css+="\n"+"    min-width: 270px;";
        css+="\n"+"    background-color: #2eb439;";
        css+="\n"+"    color: white;";
        css+="\n"+"    padding: 16px;";
        css+="\n"+"    border-radius: 8px;";
        css+="\n"+"    font-size: 1em;";
        css+="\n"+"    border: none;";
        css+="\n"+"    cursor: pointer;";
        css+="\n"+"}";
        css+="\n"+"";
        css+="\n"+".dropbtn:hover, .dropbtn:focus {";
        css+="\n"+"    background-color: #1c7224;";
        css+="\n"+"}";
        css+="\n"+"";
        css+="\n"+".dropdown {";
        css+="\n"+"    position: relative;";
        css+="\n"+"    display: inline-block;";
        css+="\n"+"}";
        css+="\n"+"";
        css+="\n"+".dropdown-content {";
        css+="\n"+"    display: none;";
        css+="\n"+"    position: absolute;";
        css+="\n"+"    background-color: #f1f1f1;";
        css+="\n"+"    min-width: 270px;";
        css+="\n"+"    overflow: auto;";
        css+="\n"+"    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);";
        css+="\n"+"    right: 0;";
        css+="\n"+"    z-index: 2;";
        css+="\n"+"}";
        css+="\n"+"";
        css+="\n"+".dropdown-content a {";
        css+="\n"+"    color: black;";
        css+="\n"+"    border-style: solid;    ";
        css+="\n"+"    border-color: #d5d5d5;";
        css+="\n"+"    border-width: thin;";
        css+="\n"+"    padding: 12px 16px;";
        css+="\n"+"    text-decoration: none;";
        css+="\n"+"    display: block;";
        css+="\n"+"}";
        css+="\n"+"";
        css+="\n"+".dropdown a:hover {";
        css+="\n"+"    background-color: #ddd;";
        css+="\n"+"}";
        css+="\n"+"";
        css+="\n"+".show {";
        css+="\n"+"    display: block;";
        css+="\n"+"    left:0;";
        css+="\n"+"    right:auto;";
        css+="\n"+"}";
        css+="\n"+"";
        css+="\n"+"#selectDropdown.dropdown-content.show {";
        css+="\n"+"    display: block;";
        css+="\n"+"    left:auto;";
        css+="\n"+"    right:0;";
        css+="\n"+"}";
        css+="\n"+"";
        css+="\n"+"em{";
        css+="\n"+"    float: right;";
        css+="\n"+"}";
        return css;
    }

    /**
     * allows to create a HTML page with its code
     * @return html all the HTML code
     */
    private static String generateHTML(){
        String html = "<!DOCTYPE HTML>";
        html+="\n"+"<html lang=\"en\">";
        html+="\n"+"    <head>";
        html+="\n"+"        <title>Visulog</title>";
        html+="\n"+"        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">";
        html+="\n"+"        <meta charset=\"utf-8\"/>";
        html+="\n"+"        <link href=\"https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@400;700&display=swap\" rel=\"stylesheet\">";
        html+="\n"+"        <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">";
        html+="\n"+"        <link rel=\"stylesheet\" href=\"style.css\">";
        html+="\n"+"   ";
        html+="\n"+"        <script type=\"text/javascript\" src=\"core/canvasjs/canvasjs.min.js\"></script>";
        html+="\n"+"        <script type=\"text/javascript\" src=\"core/jquery-1.11.1.min.js\"></script>";
        html+="\n"+"        ";
        html+="\n"+"        <script type=\"text/javascript\" src=\"data.js\"></script>";
        html+="\n"+"";
        html+="\n"+"        <script type=\"text/javascript\">";
        html+="\n"+"";
        html+="\n"+"            var chartType, textButton, title, yTitle, yFormat, chosenData;";
        html+="\n"+"   ";
        html+="\n"+"            function selectChartType(newType){";
        html+="\n"+"                chartType = newType;";
        html+="\n"+"                if(newType===\"pie\"){";
        html+="\n"+"                    document.querySelector('#buttonText2').innerHTML = \"Pie Chart  <em class=\\'fa fa-caret-down\\'></em>\";";
        html+="\n"+"                } else if(newType===\"bar\"){";
        html+="\n"+"                    document.querySelector('#buttonText2').innerHTML = \"Bar Chart  <em class=\\'fa fa-caret-down\\'></em>\";";
        html+="\n"+"                }";
        html+="\n"+"                chartGenerator(textButton, title, yTitle, yFormat, chosenData, newType);";
        html+="\n"+"            }";
        html+="\n"+"";
        html+="\n"+"            function updateData(newTextButton, newTitle, newY_Title, newY_Format, newChosenData, newType){";
        html+="\n"+"                textButton = newTextButton;";
        html+="\n"+"                title = newTitle;";
        html+="\n"+"                yTitle = newY_Title;";
        html+="\n"+"                yFormat = newY_Format;";
        html+="\n"+"                chosenData = newChosenData;";
        html+="\n"+"                chartType = newType;";
        html+="\n"+"            }";
        html+="\n"+"";
        html+="\n"+"            function chartGenerator(newTextButton, newTitle, newY_Title, newY_Format, newChosenData, newType){";
        html+="\n"+"                updateData(newTextButton, newTitle, newY_Title, newY_Format, newChosenData, newType);";
        html+="\n"+"                document.querySelector('#buttonText').innerHTML = textButton;";
        html+="\n"+"                var dataPoints = JSON.parse(chosenData);";
        html+="\n"+"                if(newType===\"pie\"){";
        html+="\n"+"                    var chart = new CanvasJS.Chart(\"chartContainer\", {";
        html+="\n"+"                    animationEnabled: true,";
        html+="\n"+"                    theme: \"light2\",";
        html+="\n"+"                    title: {";
        html+="\n"+"                        text: title,";
        html+="\n"+"                        fontFamily: \"Noto Sans JP\",";
        html+="\n"+"                        fontSize: 32";
        html+="\n"+"                    },";
        html+="\n"+"                    data: [{";
        html+="\n"+"                        type: \"pie\",";
        html+="\n"+"                        radius: \"100%\",";
        html+="\n"+"                        startAngle: 240,";
        html+="\n"+"                        indexLabel: \"{label} (#percent%)\",";
        html+="\n"+"                        dataPoints: dataPoints";
        html+="\n"+"                        }]";
        html+="\n"+"                    });";
        html+="\n"+"                } else if(newType===\"bar\"){";
        html+="\n"+"                    var chart = new CanvasJS.Chart(\"chartContainer\", {";
        html+="\n"+"                    animationEnabled: true,";
        html+="\n"+"                    theme: \"light2\",";
        html+="\n"+"                    title: {";
        html+="\n"+"                        text: title,";
        html+="\n"+"                        fontFamily: \"Noto Sans JP\",";
        html+="\n"+"                        fontSize: 32";
        html+="\n"+"                    },";
        html+="\n"+"                    axisY: {";
        html+="\n"+"                        title: yTitle,";
        html+="\n"+"                        titleFontSize: 20,";
        html+="\n"+"                        includeZero: true";
        html+="\n"+"                    },";
        html+="\n"+"                    data: [{";
        html+="\n"+"                        type: \"column\",";
        html+="\n"+"                        indexLabelFontFamily: \"Noto Sans JP\",";
        html+="\n"+"                        yValueFormatString: yFormat,";
        html+="\n"+"                        dataPoints: dataPoints";
        html+="\n"+"                    }]";
        html+="\n"+"                    });";
        html+="\n"+"                }";
        html+="\n"+"                chart.render();";
        html+="\n"+"            }";
        html+="\n"+"";
        html+="\n"+"            window.onload = function(){";
        html+="\n"+"                chartGenerator('Commits Per Developer <em class=\\'fa fa-caret-down\\'></em>', 'Number of Commits Per Developer', 'Number of Commits', '###,### commit(s)', commitData, 'bar');";
        html+="\n"+"            }";
        html+="\n"+"                ";
        html+="\n"+"        </script>";
        html+="\n"+"    </head>";
        html+="\n"+"    ";
        html+="\n"+"    <body>";
        html+="\n"+"        <div class=\"header\" id=\"lockedHeader\">";
        html+="\n"+"            <h1>Visulog</h1>";
        html+="\n"+"        </div>";
        html+="\n"+"        <div class=\"content\">";
        html+="\n"+"            <div id=\"selector\">";
        html+="\n"+"                <label id=\"selectorText\">Select a type of graph and data to view:</label>";
        html+="\n"+"                <div class=\"dropdown\">";
        html+="\n"+"                    <button id=\"buttonText2\" onclick=\"myDropdown2()\" class=\"dropbtn\">Bar Chart  <em class=\"fa fa-caret-down\"></em></button>";
        html+="\n"+"                    <div id=\"selectDropdown2\" class=\"dropdown-content\">";
        html+="\n"+"                        <a onclick=\"selectChartType('bar')\">Bar Chart</a>";
        html+="\n"+"                        <a onclick=\"selectChartType('pie')\">Pie Chart</em></a>";
        html+="\n"+"                    </div>";
        html+="\n"+"                    <button id=\"buttonText\" onclick=\"myDropdown()\" class=\"dropbtn\">Commits  <em class=\"fa fa-caret-down\"></em></button>";
        html+="\n"+"                    <div id=\"selectDropdown\" class=\"dropdown-content\">";
        html+="\n"+"                        <a onclick=\"chartGenerator('Merges  <em class=\\'fa fa-caret-down\\'></em>', 'Number of Merges Per Developer', 'Number of Merges', '###,### merge(s)', mergeData, chartType)\">Merges</a>";
        html+="\n"+"                        <a onclick=\"chartGenerator('Line Changes  <em class=\\'fa fa-caret-down\\'></em>', 'Number of Line Changes Per Developer', 'Number of Line Changes', '###,### line change(s)', lineData, chartType)\">Line Changes</a>";
        html+="\n"+"                        <a onclick=\"chartGenerator('Commits Per Developer  <em class=\\'fa fa-caret-down\\'></em>', 'Number of Commits Per Developer', 'Number of Commits', '###,### commit(s)', commitData, chartType)\">Commits Per Developer</a>";
        html+="\n"+"                        <a onclick=\"chartGenerator('Commits Per Week  <em class=\\'fa fa-caret-down\\'></em>', 'Number of Commits Per Week', 'Number of Commits', '###,### commit(s)', weekData, chartType)\">Commits Per Week</a>";
        html+="\n"+"                        <a onclick=\"chartGenerator('Commits Per Month <em class=\\'fa fa-caret-down\\'></em>', 'Number of Commits Per Month', 'Number of Commits', '###,### commit(s)', monthData, chartType)\">Commits Per Month</a>";
        html+="\n"+"                    </div>";
        html+="\n"+"                </div>";
        html+="\n"+"            </div>";
        html+="\n"+"            <div id=\"chart\"><div id=\"chartContainer\"></div></div>";
        html+="\n"+"        </div>";
        html+="\n"+"        <div class=\"footer\">";
        html+="\n"+"            <footer>Tool made by: .... </footer>";
        html+="\n"+"        </div>";
        html+="\n"+"        ";
        html+="\n"+"        <script>";
        html+="\n"+"    ";
        html+="\n"+"        function myDropdown() {";
        html+="\n"+"            document.getElementById(\"selectDropdown\").classList.toggle(\"show\");";
        html+="\n"+"        }";
        html+="\n"+"    ";
        html+="\n"+"        function myDropdown2() {";
        html+="\n"+"            document.getElementById(\"selectDropdown2\").classList.toggle(\"show\");";
        html+="\n"+"        }";
        html+="\n"+"    ";
        html+="\n"+"        window.onclick = function(event) {";
        html+="\n"+"            if (!event.target.matches('.dropbtn')) {";
        html+="\n"+"                var dropdowns = document.getElementsByClassName(\"dropdown-content\");";
        html+="\n"+"                var i;";
        html+="\n"+"                for (i = 0; i < dropdowns.length; i++) {";
        html+="\n"+"                    var openDropdown = dropdowns[i];";
        html+="\n"+"                    if (openDropdown.classList.contains('show')) {";
        html+="\n"+"                        openDropdown.classList.remove('show');";
        html+="\n"+"                    }";
        html+="\n"+"                }";
        html+="\n"+"            }";
        html+="\n"+"        }";
        html+="\n"+"        ";
        html+="\n"+"    ";
        html+="\n"+"        </script>";
        html+="\n"+"    </body>";
        html+="\n"+"</html>";
        return html;
    }

}