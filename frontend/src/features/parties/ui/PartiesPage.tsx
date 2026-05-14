import { useParties } from '../application/useParties'
import { PartyList } from './components/PartyList'

export function PartiesPage() {
  const partiesHook = useParties()

  return (
    <div className="px-4 py-8 sm:px-6 lg:px-8">
      <div className="mx-auto max-w-6xl space-y-8">
        <div>
          <p className="text-sm font-semibold uppercase tracking-[0.3em] text-orange-700">Registry</p>
          <h1 className="mt-2 font-display text-4xl leading-tight text-slate-950 sm:text-5xl">Parties</h1>
          <p className="mt-4 max-w-2xl text-lg text-slate-600">
            Manage all parties. Parties can be assigned as participants to cases.
          </p>
        </div>

        <div className="rounded-[2rem] border border-white/70 bg-white/75 p-6 shadow-[0_30px_80px_rgba(15,23,42,0.12)] backdrop-blur sm:p-8">
          <PartyList
            parties={partiesHook.parties}
            createDraft={partiesHook.createDraft}
            onCreateDraftChange={partiesHook.setCreateDraft}
            onSubmitCreate={partiesHook.submitCreate}
            editingPartyId={partiesHook.editingPartyId}
            editDraft={partiesHook.editDraft}
            onEditDraftChange={partiesHook.setEditDraft}
            onStartEditing={partiesHook.startEditing}
            onCancelEditing={partiesHook.cancelEditing}
            onSubmitEdit={partiesHook.submitEdit}
            onDelete={partiesHook.removeParty}
            errors={partiesHook.errors}
          />
        </div>
      </div>
    </div>
  )
}
