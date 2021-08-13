## Descrição completa do projeto em: [savio.pw/posts/scanned-document-organizer](https://savio.pw/posts/scanned-document-organizer).

# Tela
![Tele](resources/images/screen.gif)

# Funções
- Auto detecção da data, protocolo e tipo de documento
- Navegação entre as páginas
- Feedback do total de páginas do documento e número da página atual
- Link para o diretório e link para visualizar a página no programa padrão do SO
- Zoom nas páginas
- Tipos de documentos organizados de forma a facilitar a escolha
- Classificação de Portarias por número das páginas
- Formatação inteligente da data, protocolo e da comunicação interna
- Visualização rápida, frente e verso da folha preta
- Atalhos do teclado para agilizar o trabalho
- Opção de ver o texto extraído
- Rotação da imagem
- Opção de enviar todo o restante das páginas para o diretório Arquivos
- Botão para catar folha branca, usado caso o detector não identifique

# Auto detecção
A auto detecção é realizada a partir do segundo documento, pois para que o Regex encontre a data, protocolo e tipo do documento é realizado uma extração do texto da primeira página de cada documento. Para realizar uma auto detecão rápida, foi utilizado thread para que extraia o texto e detecte em segundo plano, assim quando o usuário clicar em organizar, os dados já estarão prontos para serem inseridos nos campos correspondentes.
