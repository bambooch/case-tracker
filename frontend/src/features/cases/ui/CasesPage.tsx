import { useState } from 'react'

import { useCasesBoard } from '../application/useCasesBoard'
import { summarizeCases } from '../domain/casePresentation'
import { CaseCreateModal } from './components/CaseCreateModal'
import { CaseList } from './components/CaseList'
import { CaseOverview } from './components/CaseOverview'

export function CasesPage() {
  const { cases, createDraft, setCreateDraft, errors, submitCreate, removeCase } = useCasesBoard()

  const [showCreateModal, setShowCreateModal] = useState(false)

  const summary = summarizeCases(cases)

  return (
    <div className="px-4 py-8 sm:px-6 lg:px-8">
      <div className="mx-auto max-w-6xl space-y-8">
        <CaseOverview total={summary.total} open={summary.open} inReview={summary.inReview} />

        {errors.delete ? (
          <p
            className="rounded-2xl border border-rose-200 bg-rose-50 px-4 py-3 text-sm font-semibold text-rose-700"
            role="alert"
          >
            {errors.delete}
          </p>
        ) : null}

        <CaseList cases={cases} onDelete={removeCase} onCreateNew={() => setShowCreateModal(true)} />
      </div>

      {showCreateModal ? (
        <CaseCreateModal
          draft={createDraft}
          onDraftChange={setCreateDraft}
          onSubmit={submitCreate}
          error={errors.create}
          onClose={() => setShowCreateModal(false)}
        />
      ) : null}
    </div>
  )
}
