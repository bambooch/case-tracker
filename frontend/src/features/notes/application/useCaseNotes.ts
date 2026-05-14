import { useState } from 'react'

import { emptyNoteDraft, type Note, type NoteDraft } from '../domain/note'
import { addNote, deleteNote, updateNote } from '../infrastructure/noteApi'

type NoteErrors = { add: string; edit: string; delete: string }
const emptyErrors: NoteErrors = { add: '', edit: '', delete: '' }

export function useCaseNotes(caseId: number, initialNotes: Note[]) {
  const [notes, setNotes] = useState<Note[]>(initialNotes)
  const [addDraft, setAddDraft] = useState<NoteDraft>(emptyNoteDraft)
  const [editingNoteId, setEditingNoteId] = useState<number | null>(null)
  const [editContent, setEditContent] = useState('')
  const [errors, setErrors] = useState<NoteErrors>(emptyErrors)

  async function submitAdd() {
    setErrors((e) => ({ ...e, add: '' }))
    try {
      const note = await addNote(caseId, addDraft)
      setNotes((prev) => [...prev, note])
      setAddDraft(emptyNoteDraft)
    } catch {
      setErrors((e) => ({ ...e, add: 'Could not add note.' }))
    }
  }

  function startEditing(note: Note) {
    setEditingNoteId(note.id)
    setEditContent(note.content)
    setErrors((e) => ({ ...e, edit: '' }))
  }

  function cancelEditing() {
    setEditingNoteId(null)
    setEditContent('')
  }

  async function submitEdit() {
    if (editingNoteId === null) return
    setErrors((e) => ({ ...e, edit: '' }))
    try {
      const updated = await updateNote(caseId, editingNoteId, editContent)
      setNotes((prev) => prev.map((n) => (n.id === updated.id ? updated : n)))
      cancelEditing()
    } catch {
      setErrors((e) => ({ ...e, edit: 'Could not update note.' }))
    }
  }

  async function removeNote(noteId: number) {
    setErrors((e) => ({ ...e, delete: '' }))
    try {
      await deleteNote(caseId, noteId)
      setNotes((prev) => prev.filter((n) => n.id !== noteId))
      if (editingNoteId === noteId) cancelEditing()
    } catch {
      setErrors((e) => ({ ...e, delete: 'Could not delete note.' }))
    }
  }

  return {
    notes,
    addDraft,
    setAddDraft,
    editingNoteId,
    editContent,
    setEditContent,
    errors,
    submitAdd,
    startEditing,
    cancelEditing,
    submitEdit,
    removeNote,
  }
}
