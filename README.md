# Tela
![Tele](resources/images/screen.gif)

# Fun��es
- Auto dete��o da data, protocolo e tipo de documento
- Visualiza��o das p�ginas do documento
- Total de p�ginas do documento
- N�mero da p�gina atual
- Link para p�gina
- Link para visualizar a p�gina no programa padr�o do SO
- Zoom nas p�ginas
- Tipos de documentos organizados de forma a facilitar a escolha
- Classifica��o de Portarias por n�mero de p�gina

# Auto detec��o
A auto detec��o � realizada a partir do segundo documento, pois para que o Regex encontre a data, protocolo e tipo do documento � realizado uma extra��o do texto da primeira p�gina de cada documento. Para realizar uma auto detec�o r�pida, foi utilizado thread para que extraia o texto e detecte em segundo plano, assim quando o usu�rio clicar em organizar, os dados j� estar�o prontos para serem inseridos nos campos correspondentes.