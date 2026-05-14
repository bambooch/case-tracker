import type { Party, PartyDraft } from '../../domain/party'
import { PartyCreateForm } from './PartyCreateForm'
import { PartyListItem } from './PartyListItem'

type Props = {
  parties: Party[]
  createDraft: PartyDraft
  onCreateDraftChange: (draft: PartyDraft) => void
  onSubmitCreate: () => Promise<void>
  editingPartyId: number | null
  editDraft: PartyDraft
  onEditDraftChange: (draft: PartyDraft) => void
  onStartEditing: (party: Party) => void
  onCancelEditing: () => void
  onSubmitEdit: () => Promise<void>
  onDelete: (partyId: number) => Promise<void>
  errors: { create: string; edit: string; delete: string }
}

export function PartyList({
  parties,
  createDraft,
  onCreateDraftChange,
  onSubmitCreate,
  editingPartyId,
  editDraft,
  onEditDraftChange,
  onStartEditing,
  onCancelEditing,
  onSubmitEdit,
  onDelete,
  errors,
}: Props) {
  return (
    <div className="space-y-4">
      <PartyCreateForm draft={createDraft} onDraftChange={onCreateDraftChange} onSubmit={onSubmitCreate} />

      {errors.create ? (
        <p className="rounded-xl border border-rose-200 bg-rose-50 px-3 py-2 text-sm font-semibold text-rose-700" role="alert">
          {errors.create}
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
        {parties.map((party) => (
          <PartyListItem
            key={party.id}
            party={party}
            editingPartyId={editingPartyId}
            editDraft={editDraft}
            onEditDraftChange={onEditDraftChange}
            onStartEditing={onStartEditing}
            onCancelEditing={onCancelEditing}
            onSubmitEdit={onSubmitEdit}
            onDelete={onDelete}
          />
        ))}
      </ul>

      {parties.length === 0 ? (
        <div className="rounded-[1.5rem] border border-dashed border-stone-300 bg-stone-50 px-6 py-8 text-center text-sm text-slate-500">
          No parties yet. Create the first one to add participants to cases.
        </div>
      ) : null}
    </div>
  )
}
