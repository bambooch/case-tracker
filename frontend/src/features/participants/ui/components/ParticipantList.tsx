import type { Party } from '../../../parties/domain/party'
import type { Participant, ParticipantDraft, ParticipantRole } from '../../domain/participant'
import { ParticipantAddForm } from './ParticipantAddForm'
import { ParticipantListItem } from './ParticipantListItem'

type Props = {
  participants: Participant[]
  addDraft: ParticipantDraft
  onAddDraftChange: (draft: ParticipantDraft) => void
  onSubmitAdd: () => Promise<void>
  editingPartyId: number | null
  editRole: ParticipantRole | ''
  onEditRoleChange: (role: ParticipantRole) => void
  onStartEditing: (participant: Participant) => void
  onCancelEditing: () => void
  onSubmitEdit: () => Promise<void>
  onRemove: (partyId: number) => Promise<void>
  errors: { add: string; edit: string; delete: string }
  parties: Party[]
}

export function ParticipantList({
  participants,
  addDraft,
  onAddDraftChange,
  onSubmitAdd,
  editingPartyId,
  editRole,
  onEditRoleChange,
  onStartEditing,
  onCancelEditing,
  onSubmitEdit,
  onRemove,
  errors,
  parties,
}: Props) {
  return (
    <div className="space-y-4">
      <ParticipantAddForm
        draft={addDraft}
        onDraftChange={onAddDraftChange}
        onSubmit={onSubmitAdd}
        parties={parties}
      />

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
        {participants.map((participant) => (
          <ParticipantListItem
            key={participant.partyId}
            participant={participant}
            editingPartyId={editingPartyId}
            editRole={editRole}
            onEditRoleChange={onEditRoleChange}
            onStartEditing={onStartEditing}
            onCancelEditing={onCancelEditing}
            onSubmitEdit={onSubmitEdit}
            onRemove={onRemove}
          />
        ))}
      </ul>

      {participants.length === 0 ? (
        <div className="rounded-[1.5rem] border border-dashed border-stone-300 bg-stone-50 px-6 py-8 text-center text-sm text-slate-500">
          No participants yet. Add one above.
        </div>
      ) : null}
    </div>
  )
}
