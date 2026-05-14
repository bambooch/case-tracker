import type { FormEvent } from 'react'

import {
  dangerButtonClasses,
  inputClasses,
  primaryButtonClasses,
  secondaryButtonClasses,
} from '../../../cases/ui/caseTheme'
import type { Party, PartyDraft } from '../../domain/party'

type Props = {
  party: Party
  editingPartyId: number | null
  editDraft: PartyDraft
  onEditDraftChange: (draft: PartyDraft) => void
  onStartEditing: (party: Party) => void
  onCancelEditing: () => void
  onSubmitEdit: () => Promise<void>
  onDelete: (partyId: number) => Promise<void>
}

export function PartyListItem({
  party,
  editingPartyId,
  editDraft,
  onEditDraftChange,
  onStartEditing,
  onCancelEditing,
  onSubmitEdit,
  onDelete,
}: Props) {
  async function handleSubmit(e: FormEvent<HTMLFormElement>) {
    e.preventDefault()
    await onSubmitEdit()
  }

  return (
    <li className="rounded-[1.5rem] border border-stone-200 bg-stone-50/80 p-4 shadow-sm transition hover:-translate-y-0.5 hover:shadow-md">
      {editingPartyId === party.id ? (
        <form className="space-y-3" onSubmit={(e) => void handleSubmit(e)}>
          <div className="grid gap-3 sm:grid-cols-2">
            <div className="space-y-1">
              <label className="text-xs font-semibold text-slate-600" htmlFor={`edit-party-name-${party.id}`}>
                Name
              </label>
              <input
                className={inputClasses}
                id={`edit-party-name-${party.id}`}
                type="text"
                value={editDraft.name}
                onChange={(e) => onEditDraftChange({ ...editDraft, name: e.target.value })}
              />
            </div>
            <div className="space-y-1">
              <label className="text-xs font-semibold text-slate-600" htmlFor={`edit-party-email-${party.id}`}>
                Email
              </label>
              <input
                className={inputClasses}
                id={`edit-party-email-${party.id}`}
                type="email"
                value={editDraft.email}
                onChange={(e) => onEditDraftChange({ ...editDraft, email: e.target.value })}
              />
            </div>
          </div>
          <div className="flex gap-2">
            <button className={primaryButtonClasses} type="submit" disabled={!editDraft.name.trim()}>
              Save changes
            </button>
            <button className={secondaryButtonClasses} type="button" onClick={onCancelEditing}>
              Cancel
            </button>
          </div>
        </form>
      ) : (
        <div className="flex flex-wrap items-center justify-between gap-3">
          <div>
            <span className="font-semibold text-slate-800">{party.name}</span>
            {party.email ? (
              <span className="ml-2 text-sm text-slate-500">{party.email}</span>
            ) : null}
          </div>
          <div className="flex flex-wrap gap-2">
            <button className={secondaryButtonClasses} type="button" onClick={() => onStartEditing(party)}>
              Edit {party.name}
            </button>
            <button className={dangerButtonClasses} type="button" onClick={() => void onDelete(party.id)}>
              Delete {party.name}
            </button>
          </div>
        </div>
      )}
    </li>
  )
}
