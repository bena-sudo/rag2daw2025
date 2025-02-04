Taules 

-- Chats
- Long idChat 
- String user 
- LocalDate date

-- Questions
- Long idQuestion
- Long idChat
- String user 
- String text
- List<ImportantIdDocumentChunkList> answerDocumentChunks

-- DocumentChunk
- Long idDocumentChunk
- ?
- ?

-- Answers
- Long idAnswer
- Long idQuestion
- String user
- String text

-- Feedback
- Long idAnswer 
- String user 
- String feedback