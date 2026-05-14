import type { FormEvent } from 'react'

import { inputClasses, primaryButtonClasses } from '../../../cases/ui/caseTheme'
import type { Party } from '../../../parties/domain/party'
import { participantRoleLabels, participantRoles, type ParticipantDraft } from '../../domain/participant'

type Props = {
  draft: ParticipantDraft
  onDraftChange: (draft: ParticipantDraft) => void
  onSubmit: () => Promise<void>
  parties: Party[]
}

export function ParticipantAddForm({ draft, onDraftChange, onSubmit, parties }: Props) {
  async function handleSubmit(e: FormEvent<HTMLFormElement>) {
    e.preventDefault()
    await onSubmit()
  }

  return (
    <form className="space-y-3" onSubmit={(e) => void handleSubmit(e)}>
      <div className="grid gap-3 sm:grid-cols-2">
        <div className="space-y-2">
          <label className="text-sm font-semibold text-slate-700" htmlFor="participant-party">
            Party
          </label>
          <select
            className={inputClasses}
            id="participant-party"
            value={draft.partyId}
            onChange={(e) =>
              onDraftChange({ ...draft, partyId: e.target.value === '' ? '' : Number(e.target.value) })
            }
          >
            <option value="" disabled>
              Select party
            </option>
            {parties.map((party) => (
              <option key={party.id} value={party.id}>
                {party.name}
              </option>
            ))}
          </select>
        </div>
        <div className="space-y-2">
          <label className="text-sm font-semibold text-slate-700" htmlFor="participant-role">
            Role
          </label>
          <select
            className={inputClasses}
            id="participant-role"
            value={draft.role}
            onChange={(e) =>
              onDraftChange({ ...draft, role: e.target.value as ParticipantDraft['role'] })
            }
          >
            <option value="" disabled>
              Select role
            </option>
            {participantRoles.map((role) => (
              <option key={role} value={role}>
                {participantRoleLabels[role]}
              </option>
            ))}
          </select>
        </div>
      </div>
      <button className={primaryButtonClasses} type="submit" disabled={!draft.partyId || !draft.role}>
        Add participant
      </button>
    </form>
  )
}
