import type { FormEvent } from 'react'

import { inputClasses, primaryButtonClasses } from '../../../cases/ui/caseTheme'
import type { NoteDraft } from '../../domain/note'

type Props = {
  draft: NoteDraft
  onDraftChange: (draft: NoteDraft) => void
  onSubmit: () => Promise<void>
}

export function NoteAddForm({ draft, onDraftChange, onSubmit }: Props) {
  async function handleSubmit(e: FormEvent<HTMLFormElement>) {
    e.preventDefault()
    await onSubmit()
  }

  return (
    <form className="space-y-3" onSubmit={(e) => void handleSubmit(e)}>
      <div className="space-y-2">
        <label className="text-sm font-semibold text-slate-700" htmlFor="note-content">
          Sadržaj
        </label>
        <textarea
          className={`${inputClasses} min-h-[5rem] resize-y`}
          id="note-content"
          name="content"
          value={draft.content}
          onChange={(e) => onDraftChange({ ...draft, content: e.target.value })}
        />
      </div>
      <div className="space-y-2">
        <label className="text-sm font-semibold text-slate-700" htmlFor="note-author">
          Autor <span className="font-normal text-slate-400">(opcionalno)</span>
        </label>
        <input
          className={inputClasses}
          id="note-author"
          name="author"
          type="text"
          value={draft.author}
          onChange={(e) => onDraftChange({ ...draft, author: e.target.value })}
        />
      </div>
      <button className={primaryButtonClasses} type="submit" disabled={!draft.content.trim()}>
        Dodaj bilješku
      </button>
    </form>
  )
}
