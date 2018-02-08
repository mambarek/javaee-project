###1) jpa

View \
Übersetzung\
Navigation\
Validierung und Fehlermeldung 
Currency converter validator


---------------------------------------------
###2) JSF JavaScript Access Example

var $element1 = $("#foo\\:bar");
// or
var $element2 = $("[id='foo:bar']");
// or
var $element3 = $(document.getElementById("foo:bar"));




$('#formId\\:inputId');
$('#formId\\:inputId').css( "border", "3px solid red" );

$('#formId\\:inputId')[0].value;
$('#formId\\:inputId')[0].style="background-color:blue";

### 3) Validation Messages

Es gibt zwei Stellen für Fehlermeldungen
- Bean Validation \
Bean Validation Messages werden in der BeanValidation jars gepflegt z.B. Hibernate Bean validation
Man kann die Meldungen überschreiben in dem man die Datei
ValidationMessages.properties bzw. ValidationMessages_xx.properties
überschreibt und direkt unter <main/java/resources> ablegt

- Validator und Converter 
Für validation and conversion Fehler kann man ein Bundle mit den Namen "Messages"unter java/resources erstellen und in 
WEB-INF/faces-config.xml. am besten den File von jsf-impl.jar kopieren und überarbeiten.

folgendes eintragen
  
      <application>
          <message-bundle>
              com.it2go.Messages
          </message-bundle>
          <locale-config>
              <default-locale>de</default-locale>
              <supported-locale>en</supported-locale>
          </locale-config>
      </application>
      
      
                      