import type { FormEvent } from 'react'

import {
  dangerButtonClasses,
  inputClasses,
  primaryButtonClasses,
  secondaryButtonClasses,
} from '../../../cases/ui/caseTheme'
import {
  participantRoleLabels,
  participantRoles,
  roleBadgeClasses,
  type Participant,
  type ParticipantRole,
} from '../../domain/participant'

type Props = {
  participant: Participant
  editingPartyId: number | null
  editRole: ParticipantRole | ''
  onEditRoleChange: (role: ParticipantRole) => void
  onStartEditing: (participant: Participant) => void
  onCancelEditing: () => void
  onSubmitEdit: () => Promise<void>
  onRemove: (partyId: number) => Promise<void>
}

export function ParticipantListItem({
  participant,
  editingPartyId,
  editRole,
  onEditRoleChange,
  onStartEditing,
  onCancelEditing,
  onSubmitEdit,
  onRemove,
}: Props) {
  async function handleSubmit(e: FormEvent<HTMLFormElement>) {
    e.preventDefault()
    await onSubmitEdit()
  }

  return (
    <li className="rounded-[1.5rem] border border-stone-200 bg-stone-50/80 p-4 shadow-sm transition hover:-translate-y-0.5 hover:shadow-md">
      {editingPartyId === participant.partyId ? (
        <form className="flex flex-wrap items-end gap-3" onSubmit={(e) => void handleSubmit(e)}>
          <div className="flex-1 min-w-[140px] space-y-1">
            <label
              className="text-xs font-semibold text-slate-600"
              htmlFor={`edit-role-${participant.partyId}`}
            >
              Uloga
            </label>
            <select
              className={inputClasses}
              id={`edit-role-${participant.partyId}`}
              value={editRole}
              onChange={(e) => onEditRoleChange(e.target.value as ParticipantRole)}
            >
              {participantRoles.map((role) => (
                <option key={role} value={role}>
                  {participantRoleLabels[role]}
                </option>
              ))}
            </select>
          </div>
          <div className="flex gap-2">
            <button className={primaryButtonClasses} type="submit">
              Spremi
            </button>
            <button className={secondaryButtonClasses} type="button" onClick={onCancelEditing}>
              Otkaži
            </button>
          </div>
        </form>
      ) : (
        <div className="flex flex-wrap items-center justify-between gap-3">
          <div className="flex items-center gap-3">
            <span className="font-semibold text-slate-800">{participant.partyName}</span>
            <span
              className={`rounded-full px-3 py-1 text-xs font-semibold tracking-[0.15em] uppercase ${roleBadgeClasses[participant.role] ?? 'bg-stone-200 text-stone-700 ring-1 ring-inset ring-stone-300'}`}
            >
              {participant.role}
            </span>
          </div>
          <div className="flex gap-2">
            <button
              className={secondaryButtonClasses}
              type="button"
              onClick={() => onStartEditing(participant)}
            >
              Uredi ulogu
            </button>
            <button
              className={dangerButtonClasses}
              type="button"
              onClick={() => void onRemove(participant.partyId)}
            >
              Ukloni
            </button>
          </div>
        </div>
      )}
    </li>
  )
}
