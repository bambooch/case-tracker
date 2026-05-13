import type { FormEvent } from 'react'

import type { CaseDraft } from '../../domain/caseDraft'
import { caseStatusLabels, caseStatusOptions } from '../../domain/casePresentation'
import { inputClasses, primaryButtonClasses } from '../caseTheme'

type CaseCreateFormProps = {
  draft: CaseDraft
  onDraftChange: (draft: CaseDraft) => void
  onSubmit: () => Promise<void>
}

export function CaseCreateForm({ draft, onDraftChange, onSubmit }: CaseCreateFormProps) {
  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()
    await onSubmit()
  }

  return (
    <section className="rounded-[2rem] border border-slate-900/10 bg-slate-950 p-6 text-stone-50 shadow-[0_30px_80px_rgba(15,23,42,0.18)] sm:p-8">
      <p className="text-sm font-semibold uppercase tracking-[0.3em] text-orange-300">Create</p>
      <h2 className="mt-3 font-display text-3xl">New case</h2>
      <form className="mt-6 space-y-4" onSubmit={(event) => void handleSubmit(event)}>
        <div className="space-y-2">
          <label className="text-sm font-semibold text-stone-200" htmlFor="case-title">
            Title
          </label>
          <input
            className={inputClasses}
            id="case-title"
            name="title"
            type="text"
            value={draft.title}
            onChange={(event) => onDraftChange({ ...draft, title: event.target.value })}
          />
        </div>
        <div className="space-y-2">
          <label className="text-sm font-semibold text-stone-200" htmlFor="case-status">
            Status
          </label>
          <select
            className={inputClasses}
            id="case-status"
            name="status"
            value={draft.status}
            onChange={(event) => onDraftChange({ ...draft, status: event.target.value as CaseDraft['status'] })}
          >
            <option value="" disabled>
              Select status
            </option>
            {caseStatusOptions.map((statusOption) => (
              <option key={statusOption} value={statusOption}>
                {caseStatusLabels[statusOption]}
              </option>
            ))}
          </select>
        </div>
        <button className={primaryButtonClasses} type="submit">
          Create case
        </button>
      </form>
    </section>
  )
}
