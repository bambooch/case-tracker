import type { FormEvent } from 'react'

import {
  dangerButtonClasses,
  inputClasses,
  primaryButtonClasses,
  secondaryButtonClasses,
} from '../../../cases/ui/caseTheme'
import type { Note } from '../../domain/note'

type Props = {
  note: Note
  editingNoteId: number | null
  editContent: string
  onEditContentChange: (content: string) => void
  onStartEditing: (note: Note) => void
  onCancelEditing: () => void
  onSubmitEdit: () => Promise<void>
  onDelete: (noteId: number) => Promise<void>
}

export function NoteListItem({
  note,
  editingNoteId,
  editContent,
  onEditContentChange,
  onStartEditing,
  onCancelEditing,
  onSubmitEdit,
  onDelete,
}: Props) {
  async function handleSubmit(e: FormEvent<HTMLFormElement>) {
    e.preventDefault()
    await onSubmitEdit()
  }

  const formattedDate = new Date(note.createdAt).toLocaleDateString('en-GB', {
    day: 'numeric',
    month: 'short',
    year: 'numeric',
  })

  return (
    <li className="rounded-[1.5rem] border border-stone-200 bg-stone-50/80 p-4 shadow-sm transition hover:-translate-y-0.5 hover:shadow-md">
      {editingNoteId === note.id ? (
        <form className="space-y-3" onSubmit={(e) => void handleSubmit(e)}>
          <textarea
            className={`${inputClasses} min-h-[5rem] resize-y`}
            value={editContent}
            onChange={(e) => onEditContentChange(e.target.value)}
          />
          <div className="flex flex-wrap gap-2">
            <button className={primaryButtonClasses} type="submit" disabled={!editContent.trim()}>
              Save
            </button>
            <button className={secondaryButtonClasses} type="button" onClick={onCancelEditing}>
              Cancel
            </button>
          </div>
        </form>
      ) : (
        <div className="flex flex-col gap-3 sm:flex-row sm:items-start sm:justify-between">
          <div className="flex-1">
            <p className="text-sm text-slate-800 whitespace-pre-wrap">{note.content}</p>
            <p className="mt-2 text-xs text-slate-400">
              {note.author ? `${note.author} · ` : ''}
              {formattedDate}
            </p>
          </div>
          <div className="flex flex-wrap gap-2 shrink-0">
            <button className={secondaryButtonClasses} type="button" onClick={() => onStartEditing(note)}>
              Edit
            </button>
            <button className={dangerButtonClasses} type="button" onClick={() => void onDelete(note.id)}>
              Delete
            </button>
          </div>
        </div>
      )}
    </li>
  )
}
