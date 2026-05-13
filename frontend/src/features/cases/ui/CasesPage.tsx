import { useCasesBoard } from '../application/useCasesBoard'
import { summarizeCases } from '../domain/casePresentation'
import { CaseCreateForm } from './components/CaseCreateForm'
import { CaseList } from './components/CaseList'
import { CaseOverview } from './components/CaseOverview'

export function CasesPage() {
  const {
    cases,
    createDraft,
    setCreateDraft,
    editingCaseId,
    editDraft,
    setEditDraft,
    errors,
    submitCreate,
    startEditing,
    cancelEditing,
    submitEdit,
    removeCase,
  } = useCasesBoard()
  const summary = summarizeCases(cases)

  return (
    <main className="min-h-screen px-4 py-6 text-slate-900 sm:px-6 lg:px-8">
      <div className="mx-auto flex w-full max-w-6xl flex-col gap-8">
        <section className="grid gap-6 lg:grid-cols-[1.2fr_0.8fr]">
          <CaseOverview total={summary.total} open={summary.open} inReview={summary.inReview} />
          <CaseCreateForm draft={createDraft} onDraftChange={setCreateDraft} onSubmit={submitCreate} />
        </section>

        {errors.create ? (
          <p className="rounded-2xl border border-rose-200 bg-rose-50 px-4 py-3 text-sm font-semibold text-rose-700" role="alert">
            {errors.create}
          </p>
        ) : null}
        {errors.edit ? (
          <p className="rounded-2xl border border-rose-200 bg-rose-50 px-4 py-3 text-sm font-semibold text-rose-700" role="alert">
            {errors.edit}
          </p>
        ) : null}
        {errors.delete ? (
          <p className="rounded-2xl border border-rose-200 bg-rose-50 px-4 py-3 text-sm font-semibold text-rose-700" role="alert">
            {errors.delete}
          </p>
        ) : null}

        <CaseList
          cases={cases}
          editingCaseId={editingCaseId}
          editDraft={editDraft}
          onEditDraftChange={setEditDraft}
          onStartEditing={startEditing}
          onCancelEditing={cancelEditing}
          onSubmitEdit={submitEdit}
          onDelete={removeCase}
        />
      </div>
    </main>
  )
}
