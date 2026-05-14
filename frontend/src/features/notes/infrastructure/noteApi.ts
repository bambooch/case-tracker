import type { Note, NoteDraft } from '../domain/note'

export async function addNote(caseId: number, draft: NoteDraft): Promise<Note> {
  const response = await fetch(`/api/cases/${caseId}/notes`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(draft),
  })
  if (!response.ok) throw new Error('Could not add note.')
  return (await response.json()) as Note
}

export async function updateNote(caseId: number, noteId: number, content: string): Promise<Note> {
  const response = await fetch(`/api/cases/${caseId}/notes/${noteId}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ content }),
  })
  if (!response.ok) throw new Error('Could not update note.')
  return (await response.json()) as Note
}

export async function deleteNote(caseId: number, noteId: number): Promise<void> {
  const response = await fetch(`/api/cases/${caseId}/notes/${noteId}`, { method: 'DELETE' })
  if (!response.ok) throw new Error('Could not delete note.')
}
