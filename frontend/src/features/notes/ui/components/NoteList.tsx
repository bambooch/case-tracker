import type { Note, NoteDraft } from '../../domain/note'
import { NoteAddForm } from './NoteAddForm'
import { NoteListItem } from './NoteListItem'

type Props = {
  notes: Note[]
  addDraft: NoteDraft
  onAddDraftChange: (draft: NoteDraft) => void
  onSubmitAdd: () => Promise<void>
  editingNoteId: number | null
  editContent: string
  onEditContentChange: (content: string) => void
  onStartEditing: (note: Note) => void
  onCancelEditing: () => void
  onSubmitEdit: () => Promise<void>
  onDelete: (noteId: number) => Promise<void>
  errors: { add: string; edit: string; delete: string }
}

export function NoteList({
  notes,
  addDraft,
  onAddDraftChange,
  onSubmitAdd,
  editingNoteId,
  editContent,
  onEditContentChange,
  onStartEditing,
  onCancelEditing,
  onSubmitEdit,
  onDelete,
  errors,
}: Props) {
  return (
    <div className="space-y-4">
      <NoteAddForm draft={addDraft} onDraftChange={onAddDraftChange} onSubmit={onSubmitAdd} />

      {errors.add ? (
        <p className="rounded-xl border border-rose-200 bg-rose-50 px-3 py-2 text-sm font-semibold text-rose-700" role="alert">
          {errors.add}
        </p>
      ) : null}
      {errors.edit ? (
        <p className="rounded-xl border border-rose-200 bg-rose-50 px-3 py-2 text-sm font-semibold text-rose-700" role="alert">
          {errors.edit}
        </p>
      ) : null}
      {errors.delete ? (
        <p className="rounded-xl border border-rose-200 bg-rose-50 px-3 py-2 text-sm font-semibold text-rose-700" role="alert">
          {errors.delete}
        </p>
      ) : null}

      <ul className="space-y-3 list-none p-0 m-0">
        {notes.map((note) => (
          <NoteListItem
            key={note.id}
            note={note}
            editingNoteId={editingNoteId}
            editContent={editContent}
            onEditContentChange={onEditContentChange}
            onStartEditing={onStartEditing}
            onCancelEditing={onCancelEditing}
            onSubmitEdit={onSubmitEdit}
            onDelete={onDelete}
          />
        ))}
      </ul>

      {notes.length === 0 ? (
        <div className="rounded-[1.5rem] border border-dashed border-stone-300 bg-stone-50 px-6 py-8 text-center text-sm text-slate-500">
          No notes yet. Add the first one above.
        </div>
      ) : null}
    </div>
  )
}
