import type { FormEvent } from 'react'

import { inputClasses, primaryButtonClasses } from '../../../cases/ui/caseTheme'
import type { PartyDraft } from '../../domain/party'

type Props = {
  draft: PartyDraft
  onDraftChange: (draft: PartyDraft) => void
  onSubmit: () => Promise<void>
}

export function PartyCreateForm({ draft, onDraftChange, onSubmit }: Props) {
  async function handleSubmit(e: FormEvent<HTMLFormElement>) {
    e.preventDefault()
    await onSubmit()
  }

  return (
    <form className="space-y-3" onSubmit={(e) => void handleSubmit(e)}>
      <div className="grid gap-3 sm:grid-cols-2">
        <div className="space-y-2">
          <label className="text-sm font-semibold text-slate-700" htmlFor="party-name">
            Name
          </label>
          <input
            className={inputClasses}
            id="party-name"
            name="name"
            type="text"
            value={draft.name}
            onChange={(e) => onDraftChange({ ...draft, name: e.target.value })}
          />
        </div>
        <div className="space-y-2">
          <label className="text-sm font-semibold text-slate-700" htmlFor="party-email">
            Email <span className="font-normal text-slate-400">(optional)</span>
          </label>
          <input
            className={inputClasses}
            id="party-email"
            name="email"
            type="email"
            value={draft.email}
            onChange={(e) => onDraftChange({ ...draft, email: e.target.value })}
          />
        </div>
      </div>
      <button className={primaryButtonClasses} type="submit" disabled={!draft.name.trim()}>
        Create party
      </button>
    </form>
  )
}
